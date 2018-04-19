package com.gino.thread.model;

/**
 * @author gino
 * Created on 2018/4/19
 */
public class SafeSubjectOne implements Subject {
    private long count = 0;
    private StringBuilder builder = new StringBuilder();

    @Override
    public synchronized void addCount() {
        this.count++;
    }

    @Override
    public synchronized void addString() {
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
