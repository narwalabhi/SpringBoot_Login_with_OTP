package com.abhishek.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.assignment.spring.repos")
@EntityScan("com.abhishek.spring.entities")
@SpringBootApplication(scanBasePackages = {"com.abhishek.spring"})
public class AssignmentApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentApiApplication.class, args);
	}

}
