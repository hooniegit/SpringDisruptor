package com.example.SpringDisruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventOneHandler implements WorkHandler<EventOne> {
    private final RingBuffer<EventTwo> ringBuffer;

    @Autowired
    public EventOneHandler(RingBuffer<EventTwo> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    @Override
    public void onEvent(EventOne eventOne) throws Exception {
        System.out.println("Handling First event " + eventOne.getMessage());

        // Publish to TransferEvent ring buffer
        long sequence = ringBuffer.next();
        try {
            EventTwo eventTwo = ringBuffer.get(sequence);
            eventTwo.setValue(eventOne.getMessage());
        } finally {
        	ringBuffer.publish(sequence);
        }
    }
}
