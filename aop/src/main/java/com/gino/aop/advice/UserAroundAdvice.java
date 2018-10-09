package com.gino.aop.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author gino
 * Created on 2018/10/9
 */
@Slf4j
public class UserAroundAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("User Around Advice Before");
        Object result = invocation.proceed();
        log.info("User Around Advice After");
        return result;
    }
}
