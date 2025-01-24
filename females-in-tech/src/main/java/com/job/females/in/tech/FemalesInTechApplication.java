package com.job.females.in.tech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FemalesInTechApplication {

	public static void main(String[] args) {
		SpringApplication.run(FemalesInTechApplication.class, args);
	}

}
