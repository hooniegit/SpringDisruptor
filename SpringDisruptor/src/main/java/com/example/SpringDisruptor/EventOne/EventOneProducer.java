package com.example.SpringDisruptor.EventOne;

import com.lmax.disruptor.RingBuffer;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventOneProducer {
    private final RingBuffer<EventOne> ringBuffer;

    @Autowired
    public EventOneProducer(RingBuffer<EventOne> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void produce(String message) {
        // [Publish] Event One
        long sequence = ringBuffer.next();
        EventOne event = ringBuffer.get(sequence);
        CompletableFuture.runAsync(() -> {
        	event.setMessage(message);
	    	ringBuffer.publish(sequence);
        });
        
        // [Initialize] Event
        event.clear();
    }
}
