package com.gino.thread.runnable;

import com.gino.thread.model.Subject;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @author gino
 * Created on 2018/4/18
 */
@Slf4j
public class MyRunnable implements Runnable {
    public static int subTimes = 100;
    private Subject subject;
    private CountDownLatch countDownLatch;

    public MyRunnable(Subject subject) {
        this.subject = subject;
    }

    public MyRunnable(Subject subject, CountDownLatch countDownLatch) {
        this.subject = subject;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        // log.info("working.....");
        for (int i = 0; i < subTimes; i++) {
            this.subject.addCount();
            this.subject.addString();
        }

        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }
}
