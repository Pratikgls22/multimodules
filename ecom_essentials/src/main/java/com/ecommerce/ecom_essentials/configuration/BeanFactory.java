package com.ecommerce.ecom_essentials.configuration;

import com.ecommerce.ecom_essentials.responseDto.TokenClaims;
import com.ecommerce.ecom_essentials.security.JwtTokenProvider;
import com.ecommerce.ecom_essentials.service.UserAuthenticationService;
import com.ecommerce.ecom_essentials.enums.ExceptionEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
@RequiredArgsConstructor
public class BeanFactory {

    private final UserAuthenticationService userAuthenticationService;
    private final JwtTokenProvider jwtTokenProvider;


    @Bean
    @RequestScope
    /*<>This Method extract JWT token from HTTPServlet Request with Jwt Token provider<>
     *@checks : token not null & token exist
     *@return : if token null empty TokenClaims object
     *
     */
    public TokenClaims claims(HttpServletRequest request, JwtTokenProvider jwtTokenProvider) {
        String token = jwtTokenProvider.resolveToken(request);
        return token != null ? tokenClaims(request) : new TokenClaims();
    }


    /*<>this method extract userName, userRole and userId from token<>
     * @param : HttpServletRequest same object from claims()
     * */
    public TokenClaims tokenClaims(HttpServletRequest request) {
        TokenClaims tokenClaims = new TokenClaims(
                this.jwtTokenProvider.resolveToken(request),
                this.jwtTokenProvider.getUserName(this.jwtTokenProvider.resolveToken(request)),
                this.jwtTokenProvider.getUserIdFromToken(this.jwtTokenProvider.resolveToken(request)),
                this.jwtTokenProvider.getUserRole(this.jwtTokenProvider.resolveToken(request)));
        return tokenClaims;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> userAuthenticationService.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(ExceptionEnum.USER_NOT_FOUND.getValue()));
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
