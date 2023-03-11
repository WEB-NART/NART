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
    public static String IPADDR2 = "192.168.0.18";
    public static String IPADDR3 = "72.141.3.152";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "**",
                        "https://imgse.com",
                        "https://s1.ax1x.com",
                        "http://" + IPADDR + ":5173",
                        "http://" + IPADDR2 + ":5173",
                        "http://" + IPADDR3 + ":5173",
                        "http://" + IPADDR + ":8888",
                        "ws://" + IPADDR + ":8888/chat",
                        "http://" + IPADDR2 + ":8888",
                        "ws://" + IPADDR2 + ":8888/chat",
                        "http://" + IPADDR3 + ":8888",
                        "ws://" + IPADDR3 + ":8888/chat"
                )
                .allowedHeaders("*")
                .allowedMethods("PUT", "GET", "POST", "DELETE")
                .allowCredentials(true)
                .maxAge(3600);

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
         //set intercept ports
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**/**")
                .excludePathPatterns("/user/login", "/user/register")
                .excludePathPatterns("/upload", "/upload/**")
                .excludePathPatterns("/static/**", "assets/**")
                .excludePathPatterns(
                    "/v2/api-docs",
                    "/swagger-resources/**",
                    "/swagger-ui.html",
                    "/webjars/**"
                );
    }


}
