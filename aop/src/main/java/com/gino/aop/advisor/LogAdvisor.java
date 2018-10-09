package com.gino.aop.advisor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author gino
 * Created on 2018/10/9
 */
@Aspect
@Component
@Slf4j
public class LogAdvisor {
    @Before(value = "@annotation(com.gino.aop.annotation.Log)")
    public void before(JoinPoint joinPoint) {
        String targetClazzName = joinPoint.getTarget().getClass().getName();
        String targetMethodName = joinPoint.getSignature().getName();
        log.info("target method: " + targetClazzName + "." + targetMethodName +" before log");
    }
}
