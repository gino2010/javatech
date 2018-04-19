package com.gino.thread;

import com.gino.thread.model.NotSafeSubject;
import com.gino.thread.model.SafeSubjectOne;
import com.gino.thread.model.SafeSubjectTwo;
import com.gino.thread.model.Subject;
import com.gino.thread.runnable.MyRunnable;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author gino
 * Created on 2018/4/18
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        ThreadFactory namedThreadFactory;
        ExecutorService threadPoolExecutor;
        // 做10次测试 do 10 tests
        int testTimes = 10;

        namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("not-safe-%d").build();
        threadPoolExecutor = new ThreadPoolExecutor(testTimes, testTimes, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        // not thread safe
        for (int i = 0; i < testTimes; i++) {
            threadPoolExecutor.execute(() -> threadCall(new NotSafeSubject()));
        }
        threadPoolExecutor.shutdown();
        while (!threadPoolExecutor.isTerminated()) {
        }

        log.info("-------------------------------------------------------------");

        namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("safe-one-%d").build();
        threadPoolExecutor = new ThreadPoolExecutor(testTimes, testTimes, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        // thread safe
        for (int i = 0; i < testTimes; i++) {
            threadPoolExecutor.execute(() -> threadCall(new SafeSubjectOne()));
        }
        threadPoolExecutor.shutdown();

        while (!threadPoolExecutor.isTerminated()) {
        }

        log.info("-------------------------------------------------------------");

        namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("safe-two-%d").build();
        threadPoolExecutor = new ThreadPoolExecutor(testTimes, testTimes, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
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
}
