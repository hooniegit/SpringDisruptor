package com.example.SpringDisruptor;

import com.lmax.disruptor.EventHandler;
import org.springframework.stereotype.Service;

@Service
public class DemoEventHandler implements EventHandler<DemoEvent> {
    @Override
    public void onEvent(DemoEvent event, long sequence, boolean endOfBatch) {
    	System.out.println("<Notify> Demo Event Task Started >>");
    	
        System.out.println("[Check] STR Val: " + event.getSTR());
        System.out.println("[Check] INT Val: " + event.getINT());
        
    	System.out.println("<Notify> Demo Event Task Finished <<");
    }
}

