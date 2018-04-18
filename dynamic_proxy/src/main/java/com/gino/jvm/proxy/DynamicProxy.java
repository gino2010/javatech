package com.gino.jvm.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * jdk proxy
 *
 * @author gino
 * Created on 2018/4/17
 */
@Slf4j
public class DynamicProxy implements InvocationHandler {
    private Object subject;

    public DynamicProxy(Object subject) {
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("ready to call method");
        log.info("Method:" + method);
        Object object = method.invoke(subject, args);
        log.info("after call method");
        return object;
    }
}
