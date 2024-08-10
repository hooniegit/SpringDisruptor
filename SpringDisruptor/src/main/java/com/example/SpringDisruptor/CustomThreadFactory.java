package com.example.SpringDisruptor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomThreadFactory implements ThreadFactory {
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    private static final ThreadLocal<Integer> threadId = ThreadLocal.withInitial(() -> 0);

    public CustomThreadFactory(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        threadId.set(threadNumber.getAndIncrement());

        Thread thread = new Thread(r, namePrefix + "-thread-" + threadId.get());

        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }

        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }

        return thread;
    }
}

