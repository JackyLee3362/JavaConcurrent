package com.day01.part3.ThreadMethods;

import com.common.util.Sleeper;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j(topic = "c.Test6")
public class Test17_ThreadSleep {

    @Test
    public void testThreadSleep() throws InterruptedException {

        Thread t = new Thread(() -> {
            Sleeper.sleep(2);
        }, "t1");

        log.debug("t1 state: {}", t.getState());
        t.start();
        log.debug("t1 state: {}", t.getState());

        Sleeper.sleep(1);
        log.debug("t1 state: {}", t.getState());
        Sleeper.sleep(2);
        log.debug("t1 state: {}", t.getState());
    }


    @Test
    public void testTimeUnit() throws InterruptedException {
        log.debug("enter");
        TimeUnit.SECONDS.sleep(1);
        log.debug("end");
    }
}
