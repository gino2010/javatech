package com.gino.aop.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * Before Advice
 *
 * @author gino
 * Created on 2018/10/9
 */
@Slf4j
public class UserBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        log.info("User Before Advice");
    }
}
