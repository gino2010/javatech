package com.gino.thread.runnable;

import com.gino.thread.model.NotSafeSubject;

/**
 * @author gino
 * Created on 2018/4/18
 */
public class MyRunnable implements Runnable{
    NotSafeSubject subject;

    public MyRunnable(NotSafeSubject subject) {
        this.subject = subject;
    }

    @Override
    public void run() {
        this.subject.addCount(1L);
        this.subject.addString("L");
    }
}
