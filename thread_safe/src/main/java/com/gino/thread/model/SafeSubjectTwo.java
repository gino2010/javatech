package com.gino.thread.model;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 线程安全类型 Thread-Safe Type
 *
 * @author gino
 * Created on 2018/4/19
 */
public class SafeSubjectTwo implements Subject {
    // CPU级别的CAS指令 CPU-level CAS instruction, best performance
    private AtomicLong count = new AtomicLong();
    // Thread-safe type also use synchronized keyword that you can find it in source code
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
