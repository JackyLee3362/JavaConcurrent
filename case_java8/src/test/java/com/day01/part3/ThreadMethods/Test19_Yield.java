package com.day01.part3.ThreadMethods;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j(topic = "c.TestYield")
public class Test19_Yield {
    static final int COUNT = 10000;

    Runnable r1 = () -> {
        int count = 0;
        while (count < COUNT) {
            log.debug("{}", count++);
        }
    };
    Runnable r2 = () -> {
        int count = 0;
        while (count < COUNT) {
            Thread.yield();
            log.debug("{}", count++);
        }
    };

    @Test
    public void testWithoutYield() throws InterruptedException {
        Thread t1 = new Thread(r1, "-----t1-----");
        Thread t2 = new Thread(r1, "t2");
        t1.setPriority(Thread.MIN_PRIORITY);
        t2.setPriority(Thread.MAX_PRIORITY);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    @Test
    public void testWithYield() throws InterruptedException {
        Thread t1 = new Thread(r1, "-----t1-----");
        Thread t2 = new Thread(r2, "t2");
        t1.setPriority(Thread.MIN_PRIORITY);
        t2.setPriority(Thread.MAX_PRIORITY);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

}
