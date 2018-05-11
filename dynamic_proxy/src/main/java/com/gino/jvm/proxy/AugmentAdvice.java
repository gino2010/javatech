package com.gino.jvm.proxy;

import com.gino.jvm.model.Augment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

/**
 * 增强引入类
 *
 * @author gino
 * Created on 2018/5/11
 */
@Slf4j
public class AugmentAdvice extends DelegatingIntroductionInterceptor implements Augment {
    @Override
    public void display(String name) {
        log.info("display: {}", name);
    }
}