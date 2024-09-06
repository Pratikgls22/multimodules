package com.ecommerce.repositry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.ecommerce.model"})
@EntityScan(basePackages = {"com.ecommerce.model"})
@ComponentScan(basePackages = {"com.ecommerce.model"})

public class RepositryApplication {

    public static void main(String[] args) {
        SpringApplication.run(RepositryApplication.class, args);
    }

}
