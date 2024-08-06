package com.example.SpringDisruptor;

import com.lmax.disruptor.EventFactory;

public class DemoEventFactory implements EventFactory<DemoEvent> {
    @Override
    public DemoEvent newInstance() {
        return new DemoEvent();
    }
}
