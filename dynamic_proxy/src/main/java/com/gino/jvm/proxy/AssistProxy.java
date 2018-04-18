package com.gino.jvm.proxy;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * javassist proxy
 *
 * @author gino
 * Created on 2018/4/18
 */
@Slf4j
public class AssistProxy implements MethodHandler {
    private ProxyFactory proxyFactory = new ProxyFactory();

    public Object getProxy(Class clazz) throws Exception {
        proxyFactory.setSuperclass(clazz);
        Class proxyClass = proxyFactory.createClass();
        Object instance = proxyClass.newInstance();
        ((ProxyObject) instance).setHandler(this);
        return instance;
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        log.info("ready to call method");
        log.info("Method:" + thisMethod);
        Object object = proceed.invoke(self, args);
        log.info("after call method");
        return object;
    }
}
