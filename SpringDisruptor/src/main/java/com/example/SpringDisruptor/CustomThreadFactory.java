package com.example.SpringDisruptor;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomThreadFactory implements ThreadFactory {
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;
    private final ThreadPoolTaskExecutor executor;

    public CustomThreadFactory(String namePrefix, ThreadPoolTaskExecutor executor) {
    	System.out.println("[ThreadFactory] CustomThreadFactory is Initialized."); // [Log]
    	
        this.namePrefix = namePrefix;
        this.executor = executor;
    }

    @Override
    public Thread newThread(Runnable r) {
    	System.out.println("[ThreadFactory] Will Return Thread"); // [Log]
    	
        Thread thread = executor.getThreadPoolExecutor().getThreadFactory().newThread(r);
        thread.setName(namePrefix + "-thread-" + threadNumber.getAndIncrement());

        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }

        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }

        return thread;
    }
}
