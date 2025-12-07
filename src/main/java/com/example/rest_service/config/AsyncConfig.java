package com.example.rest_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {

  @Bean(name = "csvProcessorExecutor") // <-- Name the bean for easy reference
  public Executor csvProcessorExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(5);        // 5 threads always active
    executor.setMaxPoolSize(10);        // Max 10 threads for bursts
    executor.setQueueCapacity(500);     // Queue up to 500 tasks if all 10 are busy
    executor.setThreadNamePrefix("CSV-Process-");
    executor.initialize();
    return executor;
  }
}