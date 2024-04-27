package com.day02.part4;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j(topic = "c.threadSafe")
public class Test16_ThreadSafe {

    static final int THREAD_NUMBER = 2;
    static final int LOOP_NUMBER = 20000;

    @Test
    void test1() {
        testThreadUnsafe();
    }

    @Test
    void test2() {
        testThreadSafe();
    }

    @Test
    void test3() {
        testThreadSafeSubClass();
    }

    // 静态方法中，局部变量引用可能会造成不安全
    static void testThreadUnsafe() {
        ThreadUnsafe obj = new ThreadUnsafe();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(() -> {
                obj.func(LOOP_NUMBER);
            }, "Thread" + (i + 1)).start();
        }
        log.debug("结束");
    }

    static void testThreadSafe() {
        ThreadSafe obj = new ThreadSafe();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(() -> {
                obj.func(LOOP_NUMBER);
            }, "Thread" + (i + 1)).start();
        }
        log.debug("结束");
    }

    static void testThreadSafeSubClass() {
        ThreadSafeSubClass obj = new ThreadSafeSubClass();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(() -> {
                obj.func(LOOP_NUMBER);
            }, "Thread" + (i + 1)).start();
        }
        log.debug("结束");
    }

    // list 是全局变量
    static class ThreadUnsafe {

        ArrayList<String> list = new ArrayList<>();

        public void func(int loopNumber) {
            for (int i = 0; i < loopNumber; i++) {
                listAdd();
                listRemove();
            }
        }

        private void listAdd() {
            list.add("1");
        }

        private void listRemove() {
            list.remove(0);
        }
    }

    // 把 list 变成局部变量
    static class ThreadSafe {

        public final void func(int loopNumber) {
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < loopNumber; i++) {
                listAdd(list);
                listRemove(list);
            }
        }

        public void listAdd(ArrayList<String> list) {
            list.add("1");
        }

        // 方法是 private 可以限制子类Override，从而达到线程安全
        private void listRemove(ArrayList<String> list) {
            list.remove(0);
        }
    }

    // todo part4 17
    // 由于创建子类，重写，所以有线程安全问题，所以要把 listRemove 改为 private 就好了
    static class ThreadSafeSubClass extends ThreadSafe {

        public void listRemove(ArrayList<String> list) {
            new Thread(() -> {
                list.remove(0);
            }).start();
        }
    }
}
