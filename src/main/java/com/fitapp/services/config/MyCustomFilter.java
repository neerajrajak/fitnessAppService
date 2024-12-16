package com.fitapp.services.config;

import jakarta.servlet.*;

import java.io.IOException;

public class MyCustomFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       System.out.println("Inside Custom Filter.");
       chain.doFilter(request, response);
        System.out.println("Custom Filter Completed.");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
