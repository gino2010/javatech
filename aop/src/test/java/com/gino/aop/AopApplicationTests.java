package com.gino.aop;

import com.gino.aop.advice.UserAfterAdvice;
import com.gino.aop.advice.UserAroundAdvice;
import com.gino.aop.advice.UserBeforeAdvice;
import com.gino.aop.advice.UserThrowAdvice;
import com.gino.aop.service.UserService;
import com.gino.aop.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AopApplicationTests {
    @Autowired
    UserService userService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testUserAdvice() {

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new UserServiceImpl());
        proxyFactory.addAdvice(new UserBeforeAdvice());
        proxyFactory.addAdvice(new UserAfterAdvice());
        UserService userService = (UserService) proxyFactory.getProxy();
        userService.query();

        proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new UserServiceImpl());
        proxyFactory.addAdvice(new UserAroundAdvice());
        userService = (UserService) proxyFactory.getProxy();
        userService.query();

        proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new UserServiceImpl());
        proxyFactory.addAdvice(new UserThrowAdvice());
        userService = (UserService) proxyFactory.getProxy();
        try {
            userService.queryWithError();
        } catch (Exception e) {

        }

        proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(this.userService);
        proxyFactory.addAdvice(new UserAroundAdvice());
        userService = (UserService) proxyFactory.getProxy();
        userService.query();
    }

    /**
     * 使用aspect注解，清爽很多
     */
    @Test
    public void testUserAdvisor(){
        this.userService.query();
    }


}
