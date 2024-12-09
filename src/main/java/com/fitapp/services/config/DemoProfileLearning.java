package com.fitapp.services.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DemoProfileLearning {

    @Value("${spring.application.name}")
    String applicationName;

    @PostConstruct
    public void printName(){
        System.out.println("Name of application is: "+applicationName);
    }
}
