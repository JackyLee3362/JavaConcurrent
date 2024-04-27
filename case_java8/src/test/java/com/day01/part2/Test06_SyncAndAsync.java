package com.day01.part2;

import com.common.constant.Constants;
import com.common.util.FileReader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 解释同步和异步的区别
 *
 * @author Jacky Lee
 * @date 2024/3/30 10:41
 */
@Slf4j(topic = "c.SyncAndAsync")
public class Test06_SyncAndAsync {

    @Test
    public void testSync() {
        FileReader.read(Constants.MP4_FULL_PATH);
        log.debug("主线程 测试同步 ...");
    }

    @Test
    public void testAsync() {
        new Thread(() -> FileReader.read(Constants.MP4_FULL_PATH)).start();
        log.debug("主线程 测试异步 ...");
    }

}
