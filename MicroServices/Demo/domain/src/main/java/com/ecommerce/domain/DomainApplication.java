package com.ecommerce.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.ecommerce.*"})
@EnableJpaAuditing
//@EntityScan(basePackages = {"com.ecommerce.*"})
//@ComponentScan(basePackages = {"com.ecommerce.*"})
//@EnableJpaRepositories(basePackages = {"com.ecommerce.*"})
public class DomainApplication {

    public static void main(String[] args) {
        SpringApplication.run(DomainApplication.class, args);
    }

}
