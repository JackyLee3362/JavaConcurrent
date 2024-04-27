package com.day01.part3.ThreadMethods;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static com.common.util.Sleeper.sleep;

@Slf4j(topic = "c.TestJoin")
public class Test24_Join {

    static int r = 0;
    static int r1 = 0;
    static int r2 = 0;

    Thread t1 = new Thread(() -> {
        log.debug("启动");
        sleep(1);
        r1 = 10;
        log.debug("结束");
    });
    Thread t2 = new Thread(() -> {
        log.debug("启动");
        sleep(2);
        r2 = 20;
        log.debug("结束");
    });

    /**
     * 测试 join 限时等待
     *
     * @author JackyLee
     * @date 2024/3/31 23:43
     */
    @Test
    public void testJoinWithTime() throws InterruptedException {

        long start = System.currentTimeMillis();
        t1.start();
        // 线程执行结束会导致 join 结束
        log.debug("join begin");
        t1.join(2000);
        long end = System.currentTimeMillis();
        log.debug("r1: {} r2: {} cost: {}", r1, r2, end - start);
    }

    /**
     * 测试多线程 join
     *
     * @author JackyLee
     * @date 2024/3/31 23:44
     */
    @Test
    public void testJoin_case2() throws InterruptedException {
        t1.start();
        t2.start();
        long start = System.currentTimeMillis();
        log.debug("join begin");
        t2.join();
        log.debug("t2 join end");
        t1.join();
        log.debug("t1 join end");
        long end = System.currentTimeMillis();
        log.debug("r1: {} r2: {} 花费时长: {} ms", r1, r2, end - start);
    }

    /**
     * 测试单线程 join
     *
     * @author JackyLee
     * @date 2024/3/31 23:45
     */
    @Test
    public void testJoin_case1() throws InterruptedException {
        log.debug("主线程 开始");
        t1.start();
        t1.join();
        log.debug("主线程 结束，结果为:{}", r);
    }
}
