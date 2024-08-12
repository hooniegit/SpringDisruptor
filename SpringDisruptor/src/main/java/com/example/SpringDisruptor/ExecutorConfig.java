package com.example.SpringDisruptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ExecutorConfig {

    @Bean(name = "customExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
    	System.out.println("[Executor] Will Return Executor"); // [Log]
    	
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(0);
        executor.setMaxPoolSize(500);
        executor.setKeepAliveSeconds(0);
        executor.setQueueCapacity(0);
        executor.setThreadNamePrefix("CustomExecutor-");
        executor.initialize();
        return executor;
    }

    @Bean(name = "customThreadFactory")
    public CustomThreadFactory customThreadFactory(ThreadPoolTaskExecutor executor) {
        return new CustomThreadFactory("CustomExecutor", executor);
    }
}
