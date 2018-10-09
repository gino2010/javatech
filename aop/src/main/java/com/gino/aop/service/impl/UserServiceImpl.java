package com.gino.aop.service.impl;

import com.gino.aop.annotation.Log;
import com.gino.aop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * User Service Implement
 *
 * @author gino
 * Created on 2018/10/9
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Log
    @Override
    public void query() {
        log.info("User Service Query");
    }

    @Override
    public void queryWithError() {
        log.info("User Service Query with Error");
        throw new RuntimeException("Error");
    }
}
