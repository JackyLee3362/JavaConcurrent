package com.day01.part3.ThreadMethods;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.LockSupport;

import static com.common.util.Sleeper.sleep;

@Slf4j(topic = "c.TestInterrupt")
public class Test25_Interrupt {

    /**
     * 打断正常运行的线程，打断标记是 true
     *
     * @author JackyLee
     * @date 2024/4/1 1:03
     */
    @Test
    void interruptRunnableThread() throws InterruptedException {
        Thread t = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                boolean interrupted = current.isInterrupted();
                if (interrupted) {
                    log.debug("被打断了");
                    break;
                }
            }
        }, "test");

        t.start();
        sleep(0.5);
        t.interrupt();

        log.debug("打断状态: {}", t.isInterrupted());
        log.debug("线程状态: {}", t.getState());
        log.debug("线程是否存活: {}", t.isAlive());

        t.join();
    }

    /**
     * 打断 sleep 中的线程，打断标记是 false
     *
     * @author Jacky Lee
     * @date 2024/4/1 1:01
     */
    @Test
    void interruptSleepThread() throws InterruptedException {
        Thread t = new Thread(() -> {
            log.info("开始 sleep");
            sleep(2);
            log.info("结束");
        }, "test");

        t.start();
        sleep(0.5);
        t.interrupt();

        log.debug("打断状态: {}", t.isInterrupted());
        log.debug("线程状态: {}", t.getState());
        log.debug("线程是否存活: {}", t.isAlive());

        t.join();
    }


    /**
     * 打断 park 中的线程，打断标记是 true
     *
     * @author Jacky Lee
     * @date 2024/4/1 1:04
     */
    @Test
    void interruptPark() throws InterruptedException {
        Thread t = new Thread(() -> {
            log.debug("park...");
            log.debug("打断前的打断标记：{}", Thread.currentThread().isInterrupted());
            LockSupport.park();
            log.debug("打断后的打断标记：{}", Thread.currentThread().isInterrupted());
        }, "test");

        t.start();
        sleep(0.5);
        t.interrupt();

        log.debug("打断状态: {}", t.isInterrupted());
        log.debug("线程状态: {}", t.getState());
        log.debug("线程是否存活: {}", t.isAlive());

        t.join();
    }

    /**
     * 如果 interrupt 标记为 true，park 还能被正常运行吗？答：不行
     *
     * @author Jacky Lee
     * @date 2024/4/1 1:05
     */
    @Test
    void interruptParkWithTrueFlag() throws InterruptedException {
        Thread t = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                log.debug("park...第 {} 次", i);
                log.debug("打断前的打断标记：{}", Thread.currentThread().isInterrupted());
                LockSupport.park();
                log.debug("打断后的打断标记：{}", Thread.currentThread().isInterrupted());
            }
        }, "test");

        t.start();
        sleep(0.5);
        t.interrupt();

        // log.debug("打断状态: {}", t.isInterrupted());
        // log.debug("线程状态: {}", t.getState());
        // log.debug("线程是否存活: {}", t.isAlive());

        // t.join();
    }
}
