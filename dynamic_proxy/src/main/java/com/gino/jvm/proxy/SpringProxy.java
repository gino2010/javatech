package com.gino.jvm.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * Spring Proxy
 *
 * @author gino
 * Created on 2018/5/10
 */
@Slf4j
public class SpringProxy implements MethodBeforeAdvice, AfterReturningAdvice {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        log.info("after method:{}", method);
    }

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        log.info("Spring AOP Proxy");
        log.info("before method:{}", method);
    }
}
