package com.gino.jvm;

import com.gino.jvm.model.Subject;
import com.gino.jvm.model.SubjectImpl;
import com.gino.jvm.proxy.CglibProxy;
import com.gino.jvm.proxy.DynamicProxy;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.beans.BeanGenerator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        log.info("return result:{}", result);

        // cglib dynamic proxy
        log.info("-----cglib-----");
        CglibProxy proxy = new CglibProxy();
        subject = (SubjectImpl) proxy.getProxy(SubjectImpl.class);
        log.info(subject.getClass().getName());
        result = subject.hello("cglib proxy");
        log.info("return result:{}", result);

        // cglib dynamic proxy object reflect private method, not call intercept method
        log.info("-----dynamic proxy object try to reflect private method-----");
        try {
            Class clazz = Class.forName("com.gino.jvm.model.SubjectImpl");
            Method myHello = clazz.getDeclaredMethod("myHello", String.class);
            myHello.setAccessible(true);
            myHello.invoke(subject, "reflect");
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // 和预想的有出入，getter没有正确返回
        // TODO: 2018/4/17 还需要调整
        BeanGenerator beanGenerator = new BeanGenerator();
        beanGenerator.addProperty("name", String.class);
        Object myBean = beanGenerator.create();
        try {
            Method setter = myBean.getClass().getMethod("setName", String.class);
            setter.invoke(myBean, "cglib generator");
            Method getter = myBean.getClass().getMethod("getName");
            log.info("result: ", getter.invoke(myBean));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
