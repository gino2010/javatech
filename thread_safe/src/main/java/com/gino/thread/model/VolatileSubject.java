package com.gino.thread.model;

/**
 * @author gino
 * Created on 2018/4/20
 */
public class VolatileSubject {
    volatile private int safeCounter;
    private int counter;

    public VolatileSubject() {
        safeCounter = 0;
        counter = 0;
    }

    public int getSafeCounter() {
        return safeCounter;
    }

    public void addSafeCounter() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.safeCounter++;
    }

    public int getCounter() {
        return counter;
    }

    public void addCounter() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.counter++;
    }
}
