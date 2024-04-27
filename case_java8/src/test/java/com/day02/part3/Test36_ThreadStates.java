package com.day02.part3;

import static com.common.util.Sleeper.sleep;

import com.common.util.Sleeper;
import java.lang.Thread.State;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 线程状态
 *
 * @author: JackyLee
 * @date 2024/4/27 下午3:42
 */
@Slf4j(topic = "c.TestState")
public class Test36_ThreadStates {

    @Test
    @DisplayName("6-Terminated 状态")
    public void testTerminated() {
        Thread t = new Thread(() -> {
        });
        t.start();

        sleep(0.5);
        Assertions.assertEquals(State.TERMINATED, t.getState());
        log.debug("状态是 {}", t.getState());
    }


    @Test
    @DisplayName("5-TimeWaiting 状态")
    public void testTimedWaiting() {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(1000000); // timed_waiting
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        });

        t.start();
        sleep(0.1);
        Assertions.assertEquals(State.TIMED_WAITING, t.getState());
        log.debug("状态是 {}", t.getState());
    }

    @Test
    @DisplayName("4-Waiting 状态")
    public void testWaiting() {
        Thread t1 = new Thread(() -> {
            while (true) {
            }
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            try {
                t1.join(); // waiting
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }, "t2");
        t2.start();
        sleep(0.2);
        Assertions.assertEquals(State.WAITING, t2.getState());
        log.debug("t5 state {}", t2.getState());
    }

    @Test
    @DisplayName("3-Blocked 状态")
    public void testBlocked() {
        Thread t1 = new Thread(() -> {
            synchronized (Test36_ThreadStates.class) {
                while (true) {
                }
            }
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            synchronized (Test36_ThreadStates.class) { // blocked
                // ...
            }
        });
        t2.start();
        sleep(0.1);
        Assertions.assertEquals(State.BLOCKED, t2.getState());
        log.debug("状态是 {}", t2.getState());
    }


    @Test
    @DisplayName("2-Runnable 状态")
    public void testRunnable() {
        Thread t = new Thread(() -> {
            while (true) {
            }
        });
        t.start();
        Assertions.assertEquals(State.RUNNABLE, t.getState());
        log.debug("状态是 {}", t.getState());
    }

    @Test
    @DisplayName("1-New 状态")
    public void testNew() {
        Thread t = new Thread(() -> {
        });

        Assertions.assertEquals(State.NEW, t.getState());
        log.debug("状态是 {}", t.getState());
    }
}
