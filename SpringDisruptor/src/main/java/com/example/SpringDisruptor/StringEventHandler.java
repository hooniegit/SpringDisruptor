package com.example.SpringDisruptor;

import com.lmax.disruptor.EventHandler;

public class StringEventHandler implements EventHandler<StringEvent>
{
    @Override
    public void onEvent(StringEvent event, long sequence, boolean endOfBatch)
    {
//        System.out.println("Event: " + event);
    }
}
