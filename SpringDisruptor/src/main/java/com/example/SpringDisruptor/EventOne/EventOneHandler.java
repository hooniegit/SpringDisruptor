package com.example.SpringDisruptor.EventOne;

import com.example.SpringDisruptor.EventTwo.EventTwo;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class EventOneHandler implements WorkHandler<EventOne> {
    private final RingBuffer<EventTwo> ringBuffer;
    private final Executor executor;

    @Autowired
    public EventOneHandler(RingBuffer<EventTwo> ringBuffer, @Qualifier("customExecutor") Executor executor) {
        this.ringBuffer = ringBuffer;
        this.executor = executor;
    }

    @Override
    public void onEvent(EventOne eventOne) throws Exception {
        // [Test] Check Current Thread
        Thread currentThread = Thread.currentThread();
        String threadName = currentThread.getName();
        long threadId = currentThread.getId();
        System.out.println("[Handler 1] Processing Thread Name: " + threadName + " | Thread ID: " + threadId);

        // [Publish] Event Two
        long sequence = ringBuffer.next();
        try {
	        EventTwo eventTwo = ringBuffer.get(sequence);
	
	        // Use the customExecutor for asynchronous execution
	        CompletableFuture.runAsync(() -> {
	            eventTwo.setValue(eventOne.getMessage());
	            ringBuffer.publish(sequence);
	        }, executor); // Specify the executor here
        } finally {
        // [Initialize] Events
//        eventTwo.clear();
//        eventOne.clear();
        }
    }
}
