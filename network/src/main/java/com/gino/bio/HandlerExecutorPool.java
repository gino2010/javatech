package com.gino.bio;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author gino
 * Created on 2018/6/28
 */
public class HandlerExecutorPool {
    private ExecutorService executor;

    public HandlerExecutorPool(int maxPoolSize, int queueSize) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();
        this.executor = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                maxPoolSize, 120L, TimeUnit.SECONDS,
                // ArrayBlockingQueue with range limit
                new ArrayBlockingQueue<>(queueSize), namedThreadFactory);
    }

    public void execute(Runnable task) {
        this.executor.execute(task);
    }
}
