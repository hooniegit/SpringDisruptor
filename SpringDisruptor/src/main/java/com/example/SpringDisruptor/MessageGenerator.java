package com.example.SpringDisruptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringDisruptor.EventOne.EventOneProducer;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

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
            
            if (i % 50 == 0) {
                ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
                int activeThreadCount = threadMXBean.getThreadCount();
                int daemonThreadCount = threadMXBean.getDaemonThreadCount();
                int peakThreadCount = threadMXBean.getPeakThreadCount();

                System.out.println(">>>    Active Thread :" + activeThreadCount);
                System.out.println(">>>    Daemon Thread :" + daemonThreadCount);
                System.out.println(">>>   Maximum Thread :" + peakThreadCount);
                
                threadMXBean = null;
            }
            
            if (i % 1000 == 0) {
            	try {
            		System.out.println("[Pause] System Sleep..");
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
            
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
    }
}
