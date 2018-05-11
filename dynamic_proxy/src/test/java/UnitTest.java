import com.gino.jvm.model.Augment;
import com.gino.jvm.model.Subject;
import com.gino.jvm.model.SubjectImpl;
import com.gino.jvm.proxy.*;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.beans.BeanGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * unit test
 *
 * @author gino
 * Created on 2018/5/10
 */
@Slf4j
@RunWith(JUnit4.class)
public class UnitTest {
    // java dynamic proxy
    @Test
    public void jdkProxy() {
        log.info("-----jdk proxy-----");
        Subject subject = new SubjectImpl();
        InvocationHandler handler = new DynamicProxy(subject);
        subject = (Subject) Proxy.newProxyInstance(handler.getClass().getClassLoader(), subject.getClass().getInterfaces(), handler);
        log.info(subject.getClass().getName());
        Boolean result = subject.hello("jdk proxy");
        log.info("return result:{}", result);
    }

    // cglib dynamic proxy
    @Test
    public void cglibProxy() {
        log.info("-----cglib-----");
        CglibProxy proxy = new CglibProxy();
        Subject subject = (SubjectImpl) proxy.getProxy(SubjectImpl.class);
        log.info(subject.getClass().getName());
        Boolean result = subject.hello("cglib proxy");
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
    }

    // javassist
    @Test
    public void javassistProxy() {
        log.info("-----Javassist-----");
        AssistProxy proxy = new AssistProxy();
        try {
            Subject subject = (SubjectImpl) proxy.getProxy(SubjectImpl.class);
            log.info(subject.getClass().getName());
            Boolean result = subject.hello("javassist");
            log.info("return result:{}", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // spring proxy
    @Test
    public void springProxy() {
        log.info("-----spring proxy-----");
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new SubjectImpl());
        proxyFactory.addAdvice(new SpringProxy());

        Subject subject = (Subject) proxyFactory.getProxy();
        Boolean result = subject.hello("spring aop");
        log.info("return result:{}", result);
    }

    @Test
    public void springAugment() {
        log.info("-----spring augment-----");
        ProxyFactory proxyFactory = new ProxyFactory();
        // proxyFactory.setInterfaces(Subject.class);
        proxyFactory.setTarget(new SubjectImpl());
        proxyFactory.addAdvice(new AugmentAdvice());
        proxyFactory.setProxyTargetClass(true);

        SubjectImpl subjectImpl = (SubjectImpl) proxyFactory.getProxy();
        subjectImpl.hello("augment");
        Augment subject = (Augment) proxyFactory.getProxy();
        log.info("augment method: ");
        subject.display("augment");
    }

    @Test
    public void tryBeanGenerator() {
        BeanGenerator beanGenerator = new BeanGenerator();
        beanGenerator.addProperty("name", String.class);

        Object myBean = beanGenerator.create();
        try {
            Method setter = myBean.getClass().getMethod("setName", String.class);
            setter.invoke(myBean, "cglib generator");
            Method getter = myBean.getClass().getMethod("getName");
            log.info("result: {}", getter.invoke(myBean));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
