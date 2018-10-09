package com.gino.aop.advice;


import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * @author gino
 * Created on 2018/10/9
 */
@Slf4j
public class UserThrowAdvice implements ThrowsAdvice {
    public void afterThrowing(Method method, Object[] args, Object target, Exception e) {
        log.info("Throw exception:");
        log.info("Target class name:{}", target.getClass().getName());
        log.info("Method name:{}", method.getName());
        log.info("Exception message:{}", e.getMessage());
    }
}
