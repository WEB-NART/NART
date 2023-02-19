package com.nart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: pack
 *
 * @className: ThreadPoolConfig
 *  Thread Pool configuration file
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2023/1/10 15:12
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {

    @Bean("taskExecutor")
    public Executor asyncServiceExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(Integer.MAX_VALUE);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("nart-app");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
    }
}

