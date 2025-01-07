package com.ecommerce.ecom_essentials.configuration;

import com.ecommerce.ecom_essentials.security.JwtTokenFilter;
import com.ecommerce.ecom_essentials.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CrossOriginFilter crossOriginFilter;
    private final AuthenticationProvider authenticationProvider;
    private final JwtTokenProvider jwtTokenProvider;

    @Primary
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        System.out.println(" securityFilter chain is running "  );
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(configure -> configure.configurationSource(crossOriginFilter.corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(this.getPublicUrls()).permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    private String[] getPublicUrls() {
        return new String[]{
                "/api/v1/auth/login",
                "/product/findStatus/status",
                "/api/v1/phone/**",
                "/product/{draftId}/rejectedProductDraft",
                "/product/fetchAllProducts",
                "/api/v1/auth/get",
                "/v1/test/login",
                "/test/multiModule/testApi",
                // for Swagger UI v2
                "/v2/api-docs",
                "/swagger-ui.html",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/webjars/**",

                // for Swagger UI v3 (OpenAPI)
                "/v3/api-docs/**",
                "/swagger-ui/**"
        };
    }
}
