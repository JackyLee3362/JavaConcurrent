package com.day02.part4;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.TestCounterUnsafe")
public class Test03_CounterUnsafe {

    static int counter = 0;
    static final int MAX = 500000;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < MAX; i++) {
                counter++;
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < MAX; i++) {
                counter--;
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("{}", counter);
    }
}
