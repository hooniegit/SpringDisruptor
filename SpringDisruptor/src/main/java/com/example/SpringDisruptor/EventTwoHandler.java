package com.example.SpringDisruptor;

import org.springframework.stereotype.Component;

import com.lmax.disruptor.WorkHandler;

@Component
public class EventTwoHandler implements WorkHandler<EventTwo> {

    @Override
    public void onEvent(EventTwo event) throws Exception {
        System.out.println("Handling Second event : " + event.getValue());
    }
}

