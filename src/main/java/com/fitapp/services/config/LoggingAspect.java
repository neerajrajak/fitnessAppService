package com.fitapp.services.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {


    @Before("execution(public String com.fitapp.services.controller.LearnAopController.fetchAnything())")
    public void beforeMethod(){
        System.out.println("Inside beforeMethod Aspect");
    }
}
