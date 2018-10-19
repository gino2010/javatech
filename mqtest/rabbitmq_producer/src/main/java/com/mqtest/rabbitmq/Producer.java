package com.mqtest.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author gino
 * Created on 2018/10/15
 */
@Slf4j
@Component
public class Producer {
    private final Queue queue;
    private final RabbitTemplate rabbitTemplate;
    @Autowired
    private Exchange exchange;

    @Autowired
    public Producer(RabbitTemplate rabbitTemplate, Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    public void sendQueue(String message) {
        log.info("send queue message: {}", message);
        this.rabbitTemplate.convertAndSend(queue.getName(), message);
    }

    public void sendBroadCast(String message) {
        log.info("send broadcast message: {}", message);
        this.rabbitTemplate.convertAndSend(exchange.getName(),"", message);
    }
}
