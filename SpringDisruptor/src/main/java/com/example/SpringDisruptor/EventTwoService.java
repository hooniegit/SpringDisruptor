package com.example.SpringDisruptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventTwoService {

    private final EventTwoProducer producer;

    @Autowired
    public EventTwoService(EventTwoProducer producer) {
        this.producer = producer;
    }

    public void createTransferEvent(String message) {
    	producer.produce(message);
    }
}
