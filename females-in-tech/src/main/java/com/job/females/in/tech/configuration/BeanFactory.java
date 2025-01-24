package com.job.females.in.tech.configuration;

import com.job.females.in.tech.enums.ExceptionEnum;
import com.job.females.in.tech.jwt.JwtProvider;
import com.job.females.in.tech.responseDto.TokenClaims;
import com.job.females.in.tech.service.UserAuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.context.annotation.RequestScope;

@Configuration
@RequiredArgsConstructor
public class BeanFactory {

    private final UserAuthenticationService userAuthenticationService;
    private final JwtProvider jwtProvider;

    @Bean
    @RequestScope
    /*<>This Method extract JWT token from HTTPServlet Request with Jwt Token provider<>
     *@checks : token not null & token exist
     *@return : if token null empty TokenClaims object
     *
     */
    public TokenClaims claims(HttpServletRequest request, JwtProvider jwtProvider) {
        String token = jwtProvider.resolveToken(request);
        return token != null ? tokenClaims(request) : new TokenClaims();
    }


    /*<>this method extract userName, userRole and userId from token<>
     * @param : HttpServletRequest same object from claims()
     * */
    public TokenClaims tokenClaims(HttpServletRequest request) {
        return new TokenClaims(
                this.jwtProvider.resolveToken(request),
                this.jwtProvider.getUserName(this.jwtProvider.resolveToken(request)),
                this.jwtProvider.getUserIdFromToken(this.jwtProvider.resolveToken(request)),
                this.jwtProvider.getUserRole(this.jwtProvider.resolveToken(request)));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userAuthenticationService.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(ExceptionEnum.USER_NOT_FOUND.getValue()));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
