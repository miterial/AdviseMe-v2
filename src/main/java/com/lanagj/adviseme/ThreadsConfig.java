package com.lanagj.adviseme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadsConfig {

    @Bean("algorithmsThreadPool")
    public ThreadPoolTaskExecutor algorithmsThreadPool() {

        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(5);
        pool.setMaxPoolSize(50);
        pool.setThreadNamePrefix("Algorithm-");
        pool.setWaitForTasksToCompleteOnShutdown(false);
        pool.setAllowCoreThreadTimeOut(true);
        return pool;
    }

    @Bean("innerCalculationsThreadPool")
    public ThreadPoolTaskExecutor innerCalculationsThreadPool() {

        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(50);
        pool.setMaxPoolSize(100);
        pool.setThreadNamePrefix("Calculations-");
        pool.setWaitForTasksToCompleteOnShutdown(false);
        pool.setAllowCoreThreadTimeOut(true);
        return pool;
    }
}
