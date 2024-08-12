package com.example.SpringDisruptor.EventTwo;

import org.springframework.stereotype.Component;

import com.lmax.disruptor.WorkHandler;

@Component
public class EventTwoHandler implements WorkHandler<EventTwo> {

    @Override
    public void onEvent(EventTwo event) throws Exception {
    	// [Task] Check Current Thread
        Thread currentThread = Thread.currentThread();
        String threadName = currentThread.getName();
        long threadId = currentThread.getId();
        System.out.println(">> [Handler 2] Processing Thread Name: " + threadName + " | Thread ID: " + threadId);
        currentThread = null;
        threadName = null;
        
        // [Test]
        System.out.println(">>>> [Handler 2] Handling Second event : " + event.getMessage());
    }
}

