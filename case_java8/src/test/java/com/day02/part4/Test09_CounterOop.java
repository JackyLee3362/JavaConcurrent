package com.day02.part4;

import lombok.extern.slf4j.Slf4j;


@Slf4j(topic="c.counterOop")
public class Test09_CounterOop {
    static final int MAX = 10000;

    public static void main(String[] args) throws InterruptedException {
        Room room = new Room();
        Thread t1 = new Thread(() -> {
            for (int j = 0; j < MAX; j++) {
                room.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int j = 0; j < MAX; j++) {
                room.decrement();
            }
        });
        t1.start();
        t2.start();

        t1.join();
        t2.join();
        log.debug("count: {}", room.get());
    }

    static class Room {

        int value = 0;

        public void increment() {
            synchronized (this) {
                value++;
            }
        }

        public void decrement() {
            synchronized (this) {
                value--;
            }
        }

        public int get() {
            synchronized (this) {
                return value;
            }
        }
    }
}
