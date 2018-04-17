package com.gino.jvm;

import com.gino.jvm.model.Subject;
import com.gino.jvm.model.SubjectImpl;
import com.gino.jvm.proxy.CglibProxy;
import com.gino.jvm.proxy.DynamicProxy;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * program entry
 *
 * @author gino
 * Created on 2018/4/17
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        // java dynamic proxy
        log.info("-----jdk proxy-----");
        Subject subject = new SubjectImpl();
        InvocationHandler handler = new DynamicProxy(subject);
        subject = (Subject) Proxy.newProxyInstance(handler.getClass().getClassLoader(), subject.getClass().getInterfaces(), handler);
        log.info(subject.getClass().getName());
        Boolean result = subject.hello("jdk proxy");
        log.info("return result{}", result);

        // cglib dynamic proxy
        log.info("-----cglib-----");
        CglibProxy proxy = new CglibProxy();
        subject = (SubjectImpl) proxy.getProxy(SubjectImpl.class);
        log.info(subject.getClass().getName());
        result = subject.hello("cglib proxy");
        log.info("return result{}", result);
    }
}
