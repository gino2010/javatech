package com.gino.thread.model;

/**
 * Not Thread Safe
 *
 * @author gino
 * Created on 2018/4/18
 */
public class NotSafeSubject implements Subject {
    private long count = 0;
    private StringBuilder builder = new StringBuilder();

    @Override
    public void addCount() {
        this.count++;
    }

    @Override
    public void addString() {
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
