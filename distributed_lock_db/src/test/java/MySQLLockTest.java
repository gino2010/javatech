import com.gino.lock.MySQLLock;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author gino
 * Created on 2018/10/22
 */
@Slf4j
@RunWith(JUnit4.class)
public class MySQLLockTest {
    private static int shared = 0;
    private static String lockName = "db_lock";

    @Test
    public void lockTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 500; i++) {
            executorService.execute(() -> {
                if (MySQLLock.getLockTimes(lockName, 3, 1000)) {
                    shared++;
                    log.info("get lock and add shared");
                    MySQLLock.releaseLock(lockName);
                    log.info("release lock....");
                }
            });
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }

        log.info("shared: {}", shared);
    }
}
