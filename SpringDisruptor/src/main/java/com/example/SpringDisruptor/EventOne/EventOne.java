package com.example.SpringDisruptor.EventOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventOne {
    private String message;
    
    public void clear() {
    	this.message = null;
    }
}

