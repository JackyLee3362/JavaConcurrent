package com.day01.part3.ThreadMethods;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.TwoPhaseTermination")
public class Test30_TwoPhaseTerminationDesign {

    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination tpt = new TwoPhaseTermination();
        tpt.start();

        Thread.sleep(3800);

        log.debug("停止监控");
        tpt.stop();
    }

    @Slf4j(topic = "c.TwoPhaseTermination")
    static class TwoPhaseTermination {

        // 监控线程
        private Thread monitorThread;

        // 停止标记
        private volatile boolean stop = false;

        // 判断是否执行过 start 方法
        private boolean hasStart = false;

        // 启动监控线程
        public void start() {
            synchronized (this) {
                // 如果执行过，不再执行，且有并发问题
                // 需要 synchronized 包围
                if (hasStart) {
                    return;
                }
                hasStart = true;
            }
            monitorThread = new Thread(() -> {
                while (true) {
                    // 是否被打断
                    if (stop) {
                        log.debug("料理后事");
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                        log.debug("执行监控记录");
                    } catch (InterruptedException e) {
                        log.error(e.getMessage());
                    }
                }
            }, "monitor");
            monitorThread.start();
        }

        // 停止监控线程
        public void stop() {
            stop = true;
            monitorThread.interrupt();
        }
    }
}

