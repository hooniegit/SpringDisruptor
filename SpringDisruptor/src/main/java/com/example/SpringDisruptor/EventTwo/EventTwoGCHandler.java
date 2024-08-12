package com.example.SpringDisruptor.EventTwo;

import com.lmax.disruptor.EventHandler;

public class EventTwoGCHandler implements EventHandler<EventTwo> {
    @Override
    public void onEvent(EventTwo event, long sequence, boolean endOfBatch) throws Exception {
    	System.out.println("[GC] Will Clean Event Two");
        event.setMessage(null);
    }
}
