package com.day01.part3.ThreadMethods;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

//

/**
 * 如何创建线程
 *
 * @link: day01-03.001 创建线程
 * @author JackyLee
 * @date 2024/3/30 11:22
 */
@Slf4j(topic = "c.createThread")
public class Test02_CreateThread {

    @Test
    public void testNewThread() {
        Thread t = new Thread(() -> log.debug("继承 Thread 类实现 ..."), "threadDemo");
        t.start();
    }

    @Test
    public void testRunnableInterface() {
        Runnable r = () -> log.debug("实现 Runnable 接口...");
        Thread t = new Thread(r, "runnableDemo");
        t.start();
    }

    @Test
    public void testFutureTask() throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("running...");
                Thread.sleep(1000);
                return 100;
            }
        });
        Thread t = new Thread(task, "futureTaskDemo");
        t.start();
        log.debug("{}", task.get());
    }

}
