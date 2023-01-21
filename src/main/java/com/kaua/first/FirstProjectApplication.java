package com.kaua.first;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class FirstProjectApplication {

	private static Logger logger = LoggerFactory.getLogger(FirstProjectApplication.class);

	public static void main(String[] args) {
		logger.info("Server starting...");
		SpringApplication.run(FirstProjectApplication.class, args);
		logger.info("Server running");
	}
}
