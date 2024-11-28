package com.ecommerce.ecom_essentials;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class EcomEssentialsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcomEssentialsApplication.class, args);
	}



}
