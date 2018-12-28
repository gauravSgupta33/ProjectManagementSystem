package com.cognizant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.cognizant.exception.ResourceNotFoundException;

@SpringBootApplication
@ComponentScan(basePackages = "com.cognizant.*")
@AutoConfigurationPackage
public class ProjectManagementSystem {

	public static void main(String[] args) throws ResourceNotFoundException {
		System.setProperty("server.port", "9080");
		System.setProperty("server.tomcat.max-threads", "1000");

		SpringApplication.run(ProjectManagementSystem.class, args);

	}
}