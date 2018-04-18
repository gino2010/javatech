package com.gino.thread.model;

import java.util.Random;

/**
 * @author gino
 * Created on 2018/4/18
 */
public class NotSafeSubject {
    private long count = 0;
    private StringBuilder builder = new StringBuilder();
    private Random random = new Random();

    public void addCount(long value) {
        try {
            Thread.sleep(random.nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.count = this.count + value;
    }

    public void addString(String text) {
        try {
            Thread.sleep(random.nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.builder.append(text);
    }

    public long getCount() {
        return count;
    }

    public StringBuilder getBuilder() {
        return builder;
    }
}
