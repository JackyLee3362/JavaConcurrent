package com.day01.part3.ThreadMethods;

import lombok.extern.slf4j.Slf4j;

/**
 * sleep 的应用，可以减少 cpu 的占用，防止cpu占用过度
 *
 * @author JackyLee
 * @date 2024/3/31 19:35
 */
@Slf4j(topic = "c.sleepApplication")
public class Test21_SleepApplication_Cpu {

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
            }
        }).start();
    }
}
