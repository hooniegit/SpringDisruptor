package com.example.SpringDisruptor;

import com.lmax.disruptor.EventFactory;

public class StringEventFactory implements EventFactory<StringEvent>
{
    @Override
    public StringEvent newInstance()
    {
    	System.out.println("[Factory] Will Return StringEvent");
        return new StringEvent();
    }
}
