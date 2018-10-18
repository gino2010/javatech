package com.mqtest.activemq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import javax.jms.Session;

/**
 * @author gino
 * Created on 2018/10/15
 */
@Slf4j
@Component
public class Consumer {
    @JmsListener(destination = "${app.queue}", containerFactory = "queueListenerFactory")
    public void receiveQueue(String message, @Headers MessageHeaders headers, Session session) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("consumer receive from queue: {}", message);
    }

    @JmsListener(destination = "${app.topic}", containerFactory = "topicListenerFactory")
    public void receiveTopic(String message, @Headers MessageHeaders headers, Session session) {
        log.info("consumer receive from topic: {}", message);
    }
}
