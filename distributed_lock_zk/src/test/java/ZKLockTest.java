import com.gino.lock.ZookeeperConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author gino
 * Created on 2018/10/22
 */
@Slf4j
@RunWith(JUnit4.class)
public class ZKLockTest {
    private static int shared = 0;

    @Test
    public void lockTest() {
        ZookeeperConfig.client.start();

        ExecutorService executorService = Executors.newFixedThreadPool(50);

        for (int i = 0; i < 500; i++) {
            executorService.execute(() -> {
                InterProcessMutex mutex = new InterProcessMutex(ZookeeperConfig.client, ZookeeperConfig.path);
                try {
                    int retryCount = 0;
                    while (true) {
                        if (retryCount > 2) {
                            log.warn("more than retry count, break.....");
                            break;
                        }
                        // 尝试获得锁的超时时间为3S，如果没有获取到则重新尝试3次。将三秒改短可以看见重试
                        boolean acquire = mutex.acquire(3, TimeUnit.SECONDS);
                        if (acquire) {
                            log.info("get lock....{}", acquire);
                            shared++;
                            break;
                        } else {
                            log.info("retry count increase");
                            retryCount++;
                            Thread.sleep(500);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (mutex.isOwnedByCurrentThread()) {
                            mutex.release();
                            log.info("release lock....");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.info("release lock error....");
                    }
                }
            });
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }

        ZookeeperConfig.client.close();
        log.info("shared: {}", shared);
    }
}
