package com.fitapp.services.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private MyCustomInterceptor myCustomInterceptor;

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(myCustomInterceptor)
                .addPathPatterns("/myapi/*")
                .excludePathPatterns("/myapi/excludeinterceptmethod");
    }
}
