package com.example.SpringDisruptor.EventOne;

import com.lmax.disruptor.RingBuffer;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EventOneProducer {
    private final RingBuffer<EventOne> ringBuffer;
    private final Executor executor;

    @Autowired
    public EventOneProducer(RingBuffer<EventOne> ringBuffer, @Qualifier("customExecutor") Executor executor) {
        this.ringBuffer = ringBuffer;
        this.executor = executor;
    }

    public void produce(String message) {
        // [Publish] Event One
        long sequence = ringBuffer.next();
        EventOne event = ringBuffer.get(sequence);
        try {
        CompletableFuture.runAsync(() -> {
        	event.setMessage(message);
	    	ringBuffer.publish(sequence);
        }, executor);
        } finally {
        // [Initialize] Event
        event.clear();
        }
    }
}
