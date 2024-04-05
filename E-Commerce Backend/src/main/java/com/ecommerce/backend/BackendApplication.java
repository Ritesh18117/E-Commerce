package com.ecommerce.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan(basePackages = "com.ecommerce.backend.entities")
public class BackendApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(BackendApplication.class, args);
		System.out.println("Started!!!!");
	}
}
