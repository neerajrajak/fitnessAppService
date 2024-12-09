package com.fitapp.services.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/learn/aop/")
public class LearnAopController {

    @GetMapping("fetchAnything")
    public String fetchAnything(){
        System.out.println("When did I printed");
        return "Why do you want to fetch anything";
    }
}
