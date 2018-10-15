package com.mqtest.activemq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author gino
 * Created on 2018/10/15
 */
@Slf4j
@Component
public class Consumer {
    @JmsListener(destination = "${app.queue}")
    public void receive(String message) {
        log.info("consumer receive: {}", message);
    }
}
