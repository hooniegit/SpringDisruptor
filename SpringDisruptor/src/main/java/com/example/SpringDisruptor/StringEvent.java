package com.example.SpringDisruptor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StringEvent
{
    private String value;

    @Override
    public String toString()
    {
        return "StringEvent{" + "value=" + value + '}';
    }
    
    void clear() {
    	value = null;
    }
}
