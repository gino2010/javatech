package com.gino.thread;

import com.gino.thread.model.NotSafeSubject;
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
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
        ExecutorService threadPoolExecutor = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 10; i++) {
            // not thread safe
            threadPoolExecutor.execute(Main::threadCall);
        }
        threadPoolExecutor.shutdown();
    }

    private static void threadCall() {
        int loopTimes = 100;
        NotSafeSubject subject = new NotSafeSubject();

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < loopTimes; i++) {
            executorService.execute(new MyRunnable(subject));
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }

        // 传统方式，traditional way
        // List<Thread> threads = new ArrayList<>(10);
        // for (int i = 0; i < loopTimes; i++) {
        //     Thread thread = new Thread(new MyRunnable(subject));
        //     thread.start();
        //     threads.add(thread);
        // }
        //
        // for (Thread thread : threads) {
        //     try {
        //         thread.join();
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        // }

        log.info("count:{}, string.size:{}, safe:{}", subject.getCount(), subject.getBuilder().toString().length(),
                subject.getCount()==loopTimes && subject.getBuilder().toString().length()==loopTimes);
    }
}
