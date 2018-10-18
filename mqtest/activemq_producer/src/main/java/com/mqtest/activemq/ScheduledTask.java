package com.mqtest.activemq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author gino
 * Created on 2018/10/18
 */
@Slf4j
@Component
public class ScheduledTask {
    private final Producer producer;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    public ScheduledTask(Producer producer) {
        this.producer = producer;
    }

    @Scheduled(fixedRate = 1000)
    public void sendTime() {
        String timeStr = dateFormat.format(new Date());
        producer.sendQueue("Producer Time: " + timeStr);
        producer.sendTopic("Producer Time: " + timeStr);
    }
}
