package com.fitapp.services.controller;

import com.fitapp.services.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/async/")
public class LearnAsyncController {

    @Autowired
    UserService userService;

    @GetMapping("user")
    public void getAsyncUserMethod(){
        System.out.println("Inside getAsyncUserMethod: "+Thread.currentThread().getName());
        userService.asyncMethodTest();
    }
}
