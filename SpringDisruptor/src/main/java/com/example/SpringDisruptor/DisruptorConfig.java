package com.example.SpringDisruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class DisruptorConfig {

    @Autowired
    private DemoEventHandler myEventHandler;

    @Bean
    public Disruptor<DemoEvent> disruptor() {
        ExecutorService executor = Executors.newCachedThreadPool();
        DemoEventFactory factory = new DemoEventFactory();
        int bufferSize = 1024; // RingBuffer의 크기 설정

        Disruptor<DemoEvent> disruptor = new Disruptor<>(
        		factory, 
        		bufferSize, 
        		executor);
        disruptor.handleEventsWith(myEventHandler);
        disruptor.start();

        return disruptor;
    }

    @Bean
    public RingBuffer<DemoEvent> ringBuffer(Disruptor<DemoEvent> disruptor) {
        return disruptor.getRingBuffer();
    }
}

