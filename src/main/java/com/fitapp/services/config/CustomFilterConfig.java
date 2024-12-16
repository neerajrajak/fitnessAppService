package com.fitapp.services.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomFilterConfig {

    public FilterRegistrationBean<MyCustomFilter> myFirstFilter(){
        FilterRegistrationBean<MyCustomFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new MyCustomFilter());
        filterFilterRegistrationBean.addUrlPatterns("/filter/*");  // for testing add any other url pattern or just only /*
        filterFilterRegistrationBean.setOrder(1);
        return filterFilterRegistrationBean;
    }
}
