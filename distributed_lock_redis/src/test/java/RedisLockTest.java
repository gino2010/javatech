import com.gino.lock.SingleRedisLock;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 单例实现测试
 *
 * @author gino
 * Created on 2018/5/2
 */
@Slf4j
@RunWith(JUnit4.class)
public class RedisLockTest {
    private static int shared = 0;
    private String lockName = "test";

    @Test
    public void lockTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 50; i++) {
            executorService.execute(() -> {
                String uuid = SingleRedisLock.getLock(lockName, 1000, 100);
                if (uuid != null) {
                    shared++;
                    SingleRedisLock.releaseLock(lockName, uuid);
                }
            });
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }
        log.info("shared: {}", shared);
    }
}
