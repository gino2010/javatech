package com.gino.thread.model;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author gino
 * Created on 2018/4/19
 */
public class SafeSubjectTwo implements Subject {
    private AtomicLong count = new AtomicLong();
    private StringBuffer builder = new StringBuffer();

    @Override
    public void addCount() {
        this.count.incrementAndGet();
    }

    @Override
    public void addString() {
        this.builder.append("T");
    }

    @Override
    public long getCount() {
        return count.get();
    }

    @Override
    public String getString() {
        return builder.toString();
    }
}
