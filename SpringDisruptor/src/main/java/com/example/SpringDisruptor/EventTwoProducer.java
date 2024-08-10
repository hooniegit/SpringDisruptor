package com.example.SpringDisruptor;

import com.lmax.disruptor.RingBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventTwoProducer {

    private final RingBuffer<EventTwo> ringBuffer;

    @Autowired
    public EventTwoProducer(RingBuffer<EventTwo> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void produce(String value) {
        long sequence = ringBuffer.next();
        try {
            EventTwo event = ringBuffer.get(sequence);
            event.setValue(value);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}

