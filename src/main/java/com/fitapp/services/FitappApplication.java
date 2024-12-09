package com.fitapp.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class }, scanBasePackages = { "com.fitapp.services" })
@EnableAsync
public class FitappApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitappApplication.class, args);
	}

}
