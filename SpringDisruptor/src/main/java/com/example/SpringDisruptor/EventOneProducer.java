package com.example.SpringDisruptor;

import com.lmax.disruptor.RingBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventOneProducer {
    private final RingBuffer<EventOne> ringBuffer;

    @Autowired
    public EventOneProducer(RingBuffer<EventOne> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void produce(String message) {
        long sequence = ringBuffer.next();
        try {
            EventOne event = ringBuffer.get(sequence);
            event.setMessage(message);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
