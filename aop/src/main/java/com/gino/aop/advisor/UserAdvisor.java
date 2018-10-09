package com.gino.aop.advisor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author gino
 * Created on 2018/10/9
 */
@Aspect
@Component
@Slf4j
public class UserAdvisor {
    /**
     * Execution表示要拦截的方法
     * <p>
     * 第一个“*”表示方法返回值任意
     * <p>
     * 第二个“*”表示匹配类中的所有方法
     * <p>
     * (..)表示方法参数任意
     */
    @Around("execution(* com.gino.aop.service.impl.UserServiceImpl.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        before();
        Object result = joinPoint.proceed();
        after();
        return result;
    }

    private void before() {
        log.info("Aspect Before");
    }

    private void after() {
        log.info("Aspect after");
    }
}
