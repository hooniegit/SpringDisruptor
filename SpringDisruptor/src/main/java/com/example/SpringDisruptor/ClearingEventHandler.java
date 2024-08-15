package com.example.SpringDisruptor;

import com.lmax.disruptor.EventHandler;

public class ClearingEventHandler implements EventHandler<StringEvent>
{
    @Override
    public void onEvent(StringEvent event, long sequence, boolean endOfBatch)
    {
        event.clear(); 
    }
}
