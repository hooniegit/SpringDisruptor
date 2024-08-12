package com.example.SpringDisruptor.EventOne;

import com.lmax.disruptor.EventHandler;

public class EventOneGCHandler implements EventHandler<EventOne> {
    @Override
    public void onEvent(EventOne event, long sequence, boolean endOfBatch) throws Exception {
    	System.out.println("[GC] Will Clean Event One");
        event.setMessage(null);
    }
}
