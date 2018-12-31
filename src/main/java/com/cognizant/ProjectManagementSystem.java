package com.cognizant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.cognizant.exception.ResourceNotFoundException;

@SpringBootApplication
@ComponentScan(basePackages = "com.cognizant.*")
@AutoConfigurationPackage
public class ProjectManagementSystem {
	private static final Logger logger = LoggerFactory.getLogger(ProjectManagementSystem.class);

	public static void main(String[] args) throws ResourceNotFoundException {
		System.setProperty("server.port", "9080");
		System.setProperty("server.tomcat.max-threads", "1000");

		SpringApplication.run(ProjectManagementSystem.class, args);
		logger.debug("-- Application started --");

	}
}