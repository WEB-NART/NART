package com.nart.config;

import com.nart.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: pack
 *
 * @className: WebMVCConfig
 *  Cors and interceptor configuration file
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2023/1/10 13:05
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    public static String IPADDR = "localhost";
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://" + IPADDR + ":8888",
                        "http://" + IPADDR + ":5173",
                        "https://imgse.com",
                        "https://s1.ax1x.com",
                        "ws://" + IPADDR + ":8888/chat")
                .allowedMethods("PUT", "DELETE", "POST", "GET")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // set intercept ports
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/register")
                .excludePathPatterns("/upload")
                .excludePathPatterns("/upload/*")
                .excludePathPatterns("/upload/delete/*");
    }
}
