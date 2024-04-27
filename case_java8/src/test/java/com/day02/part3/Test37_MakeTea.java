package com.day02.part3;

import lombok.extern.slf4j.Slf4j;

import static com.common.util.Sleeper.sleep;

@Slf4j(topic = "c.TestMakeTea")
public class Test37_MakeTea {

    public static void main(String[] args) {
        Method1.run();
        // Method2.run();
        // Method3.run();
    }

    @Slf4j(topic = "c.Method1")
    static class Method1 {

        private static void run() {
            Thread t1 = new Thread(() -> {
                log.debug("洗水壶");
                sleep(1);
                log.debug("烧开水");
                sleep(8);
            }, "老王");

            Thread t2 = new Thread(() -> {
                log.debug("洗茶壶");
                sleep(1);
                log.debug("洗茶杯");
                sleep(2);
                log.debug("拿茶叶");
                sleep(1);
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
                log.debug("泡茶");
            }, "小王");

            t1.start();
            t2.start();
        }
    }

    @Slf4j(topic = "c.Method2")
    static class Method2 {

        static String kettle = "冷水";
        static String tea = null;
        static final Object lock = new Object();
        static boolean maked = false;

        public static void run() {
            new Thread(() -> {
                log.debug("洗水壶");
                sleep(1);
                log.debug("烧开水");
                sleep(5);
                synchronized (lock) {
                    kettle = "开水";
                    lock.notifyAll();
                    while (tea == null) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            log.error(e.getMessage());
                        }
                    }
                    if (!maked) {
                        log.debug("拿({})泡({})", kettle, tea);
                        maked = true;
                    }
                }
            }, "老王").start();

            new Thread(() -> {
                log.debug("洗茶壶");
                sleep(1);
                log.debug("洗茶杯");
                sleep(2);
                log.debug("拿茶叶");
                sleep(1);
                synchronized (lock) {
                    tea = "花茶";
                    lock.notifyAll();
                    while (kettle.equals("冷水")) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            log.error(e.getMessage());
                        }
                    }
                    if (!maked) {
                        log.debug("拿({})泡({})", kettle, tea);
                        maked = true;
                    }
                }
            }, "小王").start();
        }
    }

    @Slf4j(topic = "c.Method3")
    static class Method3 {

        static String kettle = "冷水";
        static String tea = null;
        static final Object lock = new Object();

        public static void run() {
            new Thread(() -> {
                log.debug("洗水壶");
                sleep(1);
                log.debug("烧开水");
                sleep(5);
                synchronized (lock) {
                    kettle = "开水";
                    lock.notifyAll();
                }
            }, "老王").start();

            new Thread(() -> {
                log.debug("洗茶壶");
                sleep(1);
                log.debug("洗茶杯");
                sleep(2);
                log.debug("拿茶叶");
                sleep(1);
                synchronized (lock) {
                    tea = "花茶";
                    lock.notifyAll();
                }
            }, "小王").start();

            new Thread(() -> {
                synchronized (lock) {
                    while (kettle.equals("冷水") || tea == null) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            log.error(e.getMessage());
                        }
                    }
                    log.debug("拿({})泡({})", kettle, tea);
                }
            }, "王夫人").start();

        }
    }
}


