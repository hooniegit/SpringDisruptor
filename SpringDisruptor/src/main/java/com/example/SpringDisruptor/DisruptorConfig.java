package com.example.SpringDisruptor;

import com.example.SpringDisruptor.EventOne.EventOne;
import com.example.SpringDisruptor.EventOne.EventOneHandler;
import com.example.SpringDisruptor.EventTwo.EventTwo;
import com.example.SpringDisruptor.EventTwo.EventTwoHandler;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    
    @Bean
    public Disruptor<EventOne> disruptorOne(RingBuffer<EventTwo> transferRingBuffer) {
//        ThreadFactory threadFactory = Executors.defaultThreadFactory();
    	ThreadFactory threadFactory = new CustomThreadFactory("EventOneDisruptor");
        Disruptor<EventOne> disruptor = new Disruptor<>(
            EventOne::new,
            eventOneBufferSize,
            threadFactory,
            ProducerType.SINGLE,
            new BlockingWaitStrategy()
        );

        WorkHandler<EventOne>[] handlers = new EventOneHandler[eventOneThreadCount];
        for (int i = 0; i < handlers.length; i++) {
            handlers[i] = new EventOneHandler(transferRingBuffer);
        }

        disruptor.handleEventsWithWorkerPool(handlers);
        disruptor.start();

        System.out.println("Will Return Disruptor of Event ONE");
        return disruptor;
    }

    @Bean
    public RingBuffer<EventTwo> disruptorTwo() {
//        ThreadFactory threadFactory = Executors.defaultThreadFactory();
    	ThreadFactory threadFactory = new CustomThreadFactory("EventTwoDisruptor");
        Disruptor<EventTwo> disruptor = new Disruptor<>(
            EventTwo::new,
            eventTwoBufferSize,
            threadFactory,
            ProducerType.SINGLE,
            new BlockingWaitStrategy()
        );

        WorkHandler<EventTwo>[] handlers = new EventTwoHandler[eventTwoThreadCount];
        for (int i = 0; i < handlers.length; i++) {
            handlers[i] = new EventTwoHandler();
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
