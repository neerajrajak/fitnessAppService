package com.fitapp.services.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Async("javaThreadPoolExecutor")
    public void asyncMethodTest(){
        System.out.println("Inside asyncMethodTest: "+Thread.currentThread().getName());
    }
}
