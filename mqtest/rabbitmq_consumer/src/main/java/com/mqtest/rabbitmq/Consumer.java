package com.mqtest.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author gino
 * Created on 2018/10/15
 */
@Slf4j
@Component
public class Consumer {
    @RabbitListener(queues = "${app.queue}")
    public void recevie(String message) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("consumer receive queue: {}", message);
    }

    @RabbitListener(queues = "${app.broadone}")
    public void recevieOne(String message) {
        log.info("consumer receive broadcast one: {}", message);
    }

    @RabbitListener(queues = "${app.broadtwo}")
    public void recevieTwo(String message) {
        log.info("consumer receive broadcast two: {}", message);
    }
}
