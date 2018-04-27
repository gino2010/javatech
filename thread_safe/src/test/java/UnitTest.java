import com.gino.thread.model.*;
import com.gino.thread.runnable.MyRunnable;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.*;

/**
 * UnitTest
 *
 * @author gino
 * Created on 2018/4/20
 */
@Slf4j
@RunWith(JUnit4.class)
public class UnitTest {
    private ThreadFactory namedThreadFactory;
    private ExecutorService threadPoolExecutor;
    // 做10次测试 do 10 tests
    private int testTimes = 10;

    @Test
    public void testNotSafe() {
        log.info("---------------------------Not Safe-----------------------------");
        namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("not-safe-%d").build();
        // threadPoolExecutor = new ThreadPoolExecutor(testTimes, testTimes, 0L, TimeUnit.MILLISECONDS,
        //         new LinkedBlockingQueue<>(), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        // same as above
        threadPoolExecutor = Executors.newFixedThreadPool(testTimes, namedThreadFactory);
        // not thread safe
        for (int i = 0; i < testTimes; i++) {
            threadPoolExecutor.execute(() -> threadCall(new NotSafeSubject()));
        }
        threadPoolExecutor.shutdown();
        while (!threadPoolExecutor.isTerminated()) {
        }
    }

    @Test
    public void testSafeOne() {
        log.info("---------------------------Safe One-----------------------------");
        namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("safe-one-%d").build();
        threadPoolExecutor = Executors.newFixedThreadPool(testTimes, namedThreadFactory);
        // thread safe
        for (int i = 0; i < testTimes; i++) {
            threadPoolExecutor.execute(() -> threadCall(new SafeSubjectOne()));
        }
        threadPoolExecutor.shutdown();

        while (!threadPoolExecutor.isTerminated()) {
        }
    }

    @Test
    public void testSafeTwo() {
        log.info("---------------------------Safe Two-----------------------------");
        namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("safe-two-%d").build();
        threadPoolExecutor = Executors.newFixedThreadPool(testTimes, namedThreadFactory);
        // thread safe
        for (int i = 0; i < testTimes; i++) {
            threadPoolExecutor.execute(() -> threadCall(new SafeSubjectTwo()));
        }
        threadPoolExecutor.shutdown();
    }

    private static void threadCall(Subject subject) {
        // 每次测试有10个线程修改一个对象， 10 threads to modify an object per test
        int threadsNum = 10;

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < threadsNum; i++) {
            executorService.execute(new MyRunnable(subject));
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }

        // 传统方式，traditional way
        // CountDownLatch countDownLatch = new CountDownLatch(threadsNum);
        // for (int i = 0; i < threadsNum; i++) {
        //     Thread thread = new Thread(new MyRunnable(subject, countDownLatch));
        //     thread.start();
        // }
        //
        // try {
        //     countDownLatch.await();
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }

        // may be size does not equals looptimes
        log.info("count:{}, string.size:{}, safe:{}", subject.getCount(), subject.getString().length(),
                subject.getCount() == threadsNum * MyRunnable.subTimes
                        && subject.getString().length() == threadsNum * MyRunnable.subTimes);
    }

    volatile boolean safeIsRunning = true;

    // 很难停止 hard to stop, 如果debug可以正常停止 if debug this code, it can be stopped.
    // 原因在于listen thread 无法即时获得counter的变化
    // The reason is that listen thread can not get counter changes immediately
    @Test
    public void testNotVolatile() {
        VolatileSubject subject = new VolatileSubject();
        Thread runThread = new Thread(() -> {
            while (safeIsRunning) {
                subject.addCounter();
            }
            log.info("subject stop at: {}", subject.getCounter());
        });

        Thread listenThread = new Thread(() -> {
            while (true) {
                if (subject.getCounter() > 50) {
                    safeIsRunning = false;
                    break;
                }
            }
        });

        try {
            runThread.start();
            listenThread.start();
            runThread.join();
            listenThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 正常停止 normal stop, 并且counter不会比50大太多 counter will not much more than 50
    @Test
    public void testVolatile() {
        VolatileSubject subject = new VolatileSubject();
        Thread runThread = new Thread(() -> {
            while (safeIsRunning) {
                subject.addSafeCounter();
            }
            log.info("subject stop at: {}", subject.getSafeCounter());
        });

        Thread listenThread = new Thread(() -> {
            while (true) {
                if (subject.getSafeCounter() > 50) {
                    safeIsRunning = false;
                    break;
                }
            }
        });

        try {
            runThread.start();
            listenThread.start();
            runThread.join();
            listenThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
