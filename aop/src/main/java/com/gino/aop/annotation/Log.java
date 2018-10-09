package com.gino.aop.annotation;

import java.lang.annotation.*;

/**
 * @author gino
 * Created on 2018/10/9
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Log {
}
