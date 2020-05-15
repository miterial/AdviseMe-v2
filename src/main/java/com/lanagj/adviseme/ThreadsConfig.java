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
        pool.setMaxPoolSize(5);
        pool.setThreadNamePrefix("Algorithm-");
        pool.setWaitForTasksToCompleteOnShutdown(false);
        pool.setAllowCoreThreadTimeOut(true);
        return pool;
    }

    @Bean("lsaCalculationsThreadPool")
    public ThreadPoolTaskExecutor lsaCalculationsThreadPool() {

        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(6);
        pool.setMaxPoolSize(8);
        pool.setThreadNamePrefix("LSA-");
        pool.setWaitForTasksToCompleteOnShutdown(false);
        pool.setAllowCoreThreadTimeOut(true);
        return pool;
    }

    @Bean("mlsaCalculationsThreadPool")
    public ThreadPoolTaskExecutor mlsaCalculationsThreadPool() {

        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(6);
        pool.setMaxPoolSize(8);
        pool.setThreadNamePrefix("MLSA-");
        pool.setWaitForTasksToCompleteOnShutdown(false);
        pool.setAllowCoreThreadTimeOut(true);
        return pool;
    }
}
