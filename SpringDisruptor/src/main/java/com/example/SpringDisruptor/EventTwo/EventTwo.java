package com.example.SpringDisruptor.EventTwo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventTwo {
    private String value;
    
    public void clear() {
    	this.value = null;
    }
}
