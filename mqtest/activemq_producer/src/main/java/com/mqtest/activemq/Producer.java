package com.mqtest.activemq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import javax.jms.Topic;

/**
 * producer
 *
 * @author gino
 * Created on 2018/10/15
 */
@Slf4j
@Component
public class Producer {
    private final Topic topic;
    private final Queue queue;
    private final JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    public Producer(JmsMessagingTemplate jmsMessagingTemplate, Queue queue, Topic topic) {
        this.jmsMessagingTemplate = jmsMessagingTemplate;
        this.queue = queue;
        this.topic = topic;
    }

    public void sendQueue(String message) {
        log.info("activemq producer {}", message);
        this.jmsMessagingTemplate.convertAndSend(queue, message);
    }

    public void sendTopic(String message) {
        log.info("activemq producer {}", message);
        this.jmsMessagingTemplate.convertAndSend(topic, message);
    }
}
