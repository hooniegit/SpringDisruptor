package com.example.SpringDisruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

public class StringEventProducer
{
    private final RingBuffer<StringEvent> ringBuffer;

    public StringEventProducer(RingBuffer<StringEvent> ringBuffer)
    {
        this.ringBuffer = ringBuffer;
    }

    private final EventTranslatorOneArg<StringEvent, String> TRANSLATOR =
        new EventTranslatorOneArg<StringEvent, String>()
        {
            @Override
            public void translateTo(StringEvent event, long sequence, String message)
            {
//            	System.out.println("[Producer] Will Translate Data to Event");
                event.setValue(message);
            }
        };

    public void onData(String message)
    {
//    	System.out.println("[Producer] Now on Data");
        ringBuffer.publishEvent(TRANSLATOR, message);
    }
}
