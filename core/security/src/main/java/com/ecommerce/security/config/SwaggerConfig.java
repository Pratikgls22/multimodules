package com.ecommerce.security.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI api() {
        return new OpenAPI().info(
                        new Info()
                                .title("Micro-service")
                                .description("Micro-service")
                                .version("0.0.1"))
                .schemaRequirement(
                        HttpHeaders.AUTHORIZATION,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .description("<b>Jwt Token Coming From Login API</b>")
                                .name(HttpHeaders.AUTHORIZATION))
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(HttpHeaders.AUTHORIZATION, HttpHeaders.AUTHORIZATION));
    }
}
