package com.example.SpringDisruptor;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.ByteBuffer;

@Configuration
public class DisruptorConfig {

    @Bean
    public Disruptor<StringEvent> disruptor() {
        int bufferSize = 1024;

        Disruptor<StringEvent> disruptor = new Disruptor<>(StringEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);
//        disruptor.handleEventsWith((EventHandler<StringEvent>) (event, sequence, endOfBatch) -> {
//            System.out.println("Event: " + event.getValue());
//        });
        
        disruptor.handleEventsWith(new StringEventHandler())
        		 .then(new ClearingEventHandler());
        disruptor.start();
        
        return disruptor;
    }

    @Bean
    public RingBuffer<StringEvent> ringBuffer(Disruptor<StringEvent> disruptor) {
    	System.out.println("[Config] Will Return Ring Buffer");
        return disruptor.getRingBuffer();
    }

    @Bean
    public StringEventProducer eventProducer(RingBuffer<StringEvent> ringBuffer) {
    	System.out.println("[Config] Will Return EventProducer");
        return new StringEventProducer(ringBuffer);
    }
}

