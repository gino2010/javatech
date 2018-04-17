package com.gino.jvm.model;

import lombok.extern.slf4j.Slf4j;

/**
 * @author gino
 * Created on 2018/4/17
 */
@Slf4j
public class SubjectImpl implements Subject {
    @Override
    public Boolean hello(String str) {
        log.info("hello: " + str);
        return true;
    }

    private Boolean myHello(String str) {
        log.info("private hello: " + str);
        return true;
    }
}
