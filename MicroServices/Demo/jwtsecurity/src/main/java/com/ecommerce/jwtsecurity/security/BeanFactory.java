package com.ecommerce.jwtsecurity.security;

import com.ecommerce.model.dto.TokenClaims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
@RequiredArgsConstructor
public class BeanFactory {

    private final JwtTokenProvider jwtService;
    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        System.out.println(provider);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    @RequestScope
    public TokenClaims claims(HttpServletRequest request, HttpServletResponse response) {
        String token = jwtService.resolveToken(request);
        return token != null ? tokenClamis(request) : new TokenClaims();

    }

    private TokenClaims tokenClamis(HttpServletRequest request) {
        String token = this.jwtService.resolveToken(request);
        String userName = this.jwtService.getUserNameFromToken(token);
        Long userId = Long.valueOf(this.jwtService.getUserIdFromToken(token));

        return new TokenClaims(token, userName, userId);
    }

    //    @Bean
//    public UserDetailsService userDetailsService() {
//        return new MyUserDetailsService();
//    } ** In case i use this Bean by fault then use @Primary annotation on MyUserDetailsService class. **
}
