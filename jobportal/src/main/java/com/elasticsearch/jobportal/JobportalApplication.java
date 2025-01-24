package com.elasticsearch.jobportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = "com.elasticsearch.jobportal.*")
@EnableJpaRepositories(basePackages = "com.elasticsearch.jobportal.repository")
@EnableElasticsearchRepositories(basePackages = "com.elasticsearch.jobportal.elasticRepository")
public class JobportalApplication {

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	public static void main(String[] args) {
		SpringApplication.run(JobportalApplication.class, args);
	}

}
