package com.gino.thread.model;

/**
 * @author gino
 * Created on 2018/4/19
 */
public interface Subject {
    void addCount();
    void addString();
    long getCount();
    String getString();
}
