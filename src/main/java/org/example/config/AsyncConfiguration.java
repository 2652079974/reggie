package org.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author Maplerain
 * @date 2023/4/20 19:54
 **/
@Configuration
@EnableAsync
public class AsyncConfiguration {

    @Value("${thread-pool.core-pool-size}")
    private Integer corePoolSize;

    @Value("${thread-pool.max-pool-size}")
    private Integer maxPoolSize;

    @Value("${thread-pool.queue-capacity}")
    private Integer queueCapacity;

    @Value("${thread-pool.thread-name-prefix}")
    private String threadNamePrefix;

    @Bean
    public Executor threadPoolTaskExecution(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.initialize();
        return executor;
    }
}
