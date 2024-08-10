package com.example.SpringDisruptor.EventOne;

import com.example.SpringDisruptor.EventTwo.EventTwo;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventOneHandler implements WorkHandler<EventOne> {
    private final RingBuffer<EventTwo> ringBuffer;

    @Autowired
    public EventOneHandler(RingBuffer<EventTwo> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    @Override
    public void onEvent(EventOne eventOne) throws Exception {
    	// [Test] Check Current Thread
        Thread currentThread = Thread.currentThread();
        String threadName = currentThread.getName();
        long threadId = currentThread.getId();
        System.out.println("[Handler 1] Processing Thread Name: " + threadName + " | Thread ID: " + threadId);
        
        // [Initialize] Thread
        currentThread = null;
    	
        // [Publish] Event Two
        long sequence = ringBuffer.next();
        EventTwo eventTwo = ringBuffer.get(sequence);
        CompletableFuture.runAsync(() -> {
	        eventTwo.setValue(eventOne.getMessage());
	        ringBuffer.publish(sequence);
        });
        
        // [Initialize] Events
        eventTwo.clear();
        Thread.sleep(1000);
        eventOne.clear();
    }
}
