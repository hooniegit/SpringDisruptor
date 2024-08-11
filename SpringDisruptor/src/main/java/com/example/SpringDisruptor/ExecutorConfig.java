package com.example.SpringDisruptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ExecutorConfig {

    @Bean(name = "customExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(0); // 최소 스레드 수
        executor.setMaxPoolSize(10); // 최대 스레드 수
        executor.setKeepAliveSeconds(0); // 유휴 스레드 유지 시간
        executor.setQueueCapacity(0); // 큐 크기 (0으로 설정 시 SynchronousQueue 사용)
        executor.setThreadNamePrefix("CustomExecutor-");
        executor.initialize();
        return executor;
    }
}

