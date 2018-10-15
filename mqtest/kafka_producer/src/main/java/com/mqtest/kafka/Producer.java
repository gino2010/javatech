package com.mqtest.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * producer component
 *
 * @author gino
 * Created on 2018/10/15
 */
@Slf4j
@Component
public class Producer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.topic}")
    private String topic;

    @Autowired
    public Producer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String message) {
        this.kafkaTemplate.send(topic, message);
        log.info("produce message {}", message);
    }
}
