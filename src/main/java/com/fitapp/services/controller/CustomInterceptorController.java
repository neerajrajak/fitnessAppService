package com.fitapp.services.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("myapi/")
public class CustomInterceptorController {

    @GetMapping("testInterceptor")
    public String testInterceptor(){
        return "Hit";
    }
}
