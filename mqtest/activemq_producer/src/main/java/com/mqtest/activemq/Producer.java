package com.mqtest.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

/**
 * producer
 *
 * @author gino
 * Created on 2018/10/15
 */
@Component
public class Producer {
    private final Queue queue;
    private final JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    public Producer(JmsMessagingTemplate jmsMessagingTemplate, Queue queue) {
        this.jmsMessagingTemplate = jmsMessagingTemplate;
        this.queue = queue;
    }

    public void send(String message) {
        this.jmsMessagingTemplate.convertAndSend(queue, message);
    }
}
