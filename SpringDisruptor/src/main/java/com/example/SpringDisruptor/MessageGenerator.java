package com.example.SpringDisruptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class MessageGenerator {

    private final EventOneProducer producer;

    @Autowired
    public MessageGenerator(EventOneProducer producer) {
        this.producer = producer;
    }

    @PostConstruct
    public void generateMessages() {
        for (int i = 1; i < 50000001; i++) {
            String message = "Message " + i;
            producer.produce(message);
            try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
    }
}
