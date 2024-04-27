package com.day01.part3.ThreadMethods;

import com.common.constant.Constants;
import com.common.util.FileReader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 问：run 方法 和 start 方法启动有什么区别？ 答：可以看到 run 方法是 main 线程执行的
 *
 * @author: JackyLee
 * @date 2024/4/27 下午3:32
 */
@Slf4j(topic = "c.startVsRun")
public class Test15_StartVsRun {

    Thread t = new Thread(() -> {
        log.debug("running...");
        FileReader.read(Constants.MP4_FULL_PATH);
    });

    @Test
    public void testStart() {
        t.start();
        log.debug("主线程");
    }

    @Test
    public void testRun() {
        log.debug("主线程");
        t.run();
        log.debug("主线程 在做其他事情 ... ");
    }
}
