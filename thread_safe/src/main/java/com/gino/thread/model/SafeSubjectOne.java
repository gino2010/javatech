package com.gino.thread.model;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 同步方式 synchronized or lock
 * 是需要消耗系统作为代价的 Thread-Safe is on expense of system resource
 * 关于lock的使用，可能还需要另一个详细的例子
 * About the use of locks may require another more detailed example
 *
 * @author gino
 * Created on 2018/4/19
 */
public class SafeSubjectOne implements Subject {
    private long count = 0;
    private StringBuilder builder = new StringBuilder();
    private ReentrantLock lock = new ReentrantLock();

    @Override
    public void addCount() {
        lock.lock();
        try {
            this.count++;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public synchronized void addString() {
        // if you don't want to synchronize method, you can synchronize object
        // synchronized (builder){
        //     this.builder.append("T");
        // }
        this.builder.append("T");
    }

    @Override
    public long getCount() {
        return count;
    }

    @Override
    public String getString() {
        return builder.toString();
    }
}
