package com.example.SpringDisruptor;

import com.example.SpringDisruptor.EventOne.EventOne;
import com.example.SpringDisruptor.EventOne.EventOneHandler;
import com.example.SpringDisruptor.EventTwo.EventTwo;
import com.example.SpringDisruptor.EventTwo.EventTwoHandler;

import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WorkHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

@Configuration
public class DisruptorConfig {

    @Value("${disruptor.eventOne.bufferSize}")
    private int eventOneBufferSize;

    @Value("${disruptor.eventTwo.bufferSize}")
    private int eventTwoBufferSize;

    @Value("${disruptor.eventOne.threadCount}")
    private int eventOneThreadCount;

    @Value("${disruptor.eventTwo.threadCount}")
    private int eventTwoThreadCount;

    private final Executor executor;
    private final ApplicationContext context;

    @Autowired
    public DisruptorConfig(@Qualifier("customExecutor") Executor executor, ApplicationContext context) {
        this.executor = executor;
        this.context = context;
    }
    
    @Bean
    public Disruptor<EventOne> disruptorOne(RingBuffer<EventTwo> transferRingBuffer) {
        ThreadFactory threadFactory = new CustomThreadFactory("EventOneDisruptor");
        Disruptor<EventOne> disruptor = new Disruptor<>(
            EventOne::new,
            eventOneBufferSize,
            threadFactory,
            ProducerType.SINGLE,
            new SleepingWaitStrategy()
        );

        // Use ApplicationContext to get EventOneHandler beans
        WorkHandler<EventOne>[] handlers = new EventOneHandler[eventOneThreadCount];
        for (int i = 0; i < handlers.length; i++) {
            // Retrieve EventOneHandler bean from context with all dependencies injected
            handlers[i] = context.getBean(EventOneHandler.class);
        }

        disruptor.handleEventsWithWorkerPool(handlers);
        disruptor.start();

        System.out.println("Will Return Disruptor of Event ONE");
        return disruptor;
    }

    @Bean
    public RingBuffer<EventTwo> disruptorTwo() {
        ThreadFactory threadFactory = new CustomThreadFactory("EventTwoDisruptor");
        Disruptor<EventTwo> disruptor = new Disruptor<>(
            EventTwo::new,
            eventTwoBufferSize,
            threadFactory,
            ProducerType.SINGLE,
            new SleepingWaitStrategy()
        );

        WorkHandler<EventTwo>[] handlers = new EventTwoHandler[eventTwoThreadCount];
        for (int i = 0; i < handlers.length; i++) {
            handlers[i] = context.getBean(EventTwoHandler.class);
        }

        disruptor.handleEventsWithWorkerPool(handlers);
        disruptor.start();

        System.out.println("Will Return Disruptor of Event TWO");
        return disruptor.getRingBuffer();
    }

    @Bean
    public RingBuffer<EventOne> ringBufferOne(Disruptor<EventOne> disruptor) {
        System.out.println("Will Return Ring Buffer of Event ONE");
        return disruptor.getRingBuffer();
    }
}
