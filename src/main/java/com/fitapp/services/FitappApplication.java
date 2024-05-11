package com.fitapp.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class }, scanBasePackages = { "com.fitapp.services" })
public class FitappApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitappApplication.class, args);
	}

}
