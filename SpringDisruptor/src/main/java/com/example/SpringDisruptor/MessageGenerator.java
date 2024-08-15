package com.example.SpringDisruptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import javax.annotation.PostConstruct;

@Service
public class MessageGenerator {

    private final StringEventProducer producer;

    @Autowired
    public MessageGenerator(StringEventProducer producer) {
        this.producer = producer;
    }
    
    @PostConstruct
    public void generate() {
        int cnt = 0;
        
        while (true) {
            cnt += 1;
            if (cnt % 1001 == 0) {
                cnt = 0;
                
                // Thread 정보 출력
                ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
                int activeThreadCount = threadMXBean.getThreadCount();
                int daemonThreadCount = threadMXBean.getDaemonThreadCount();
                int peakThreadCount = threadMXBean.getPeakThreadCount();

                System.out.println(">>>    Active Thread :" + activeThreadCount);
                System.out.println(">>>    Daemon Thread :" + daemonThreadCount);
                System.out.println(">>>   Maximum Thread :" + peakThreadCount);
                
                // 메모리 정보 출력
                Runtime runtime = Runtime.getRuntime();

                long totalMemory = runtime.totalMemory();
                long freeMemory = runtime.freeMemory();
                long maxMemory = runtime.maxMemory();

                System.out.println("Total Memory: " + totalMemory + " bytes");
                System.out.println("Free Memory: " + freeMemory + " bytes");
                System.out.println("Max Memory: " + maxMemory + " bytes");
                
                try {
                    Thread.sleep(990);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
            } else {
                // 메시지 생성 및 발행
                producer.onData("[Message]" + cnt);
            }
            
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
