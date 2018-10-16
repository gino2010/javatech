package com.mqtest.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Consumer
 *
 * @author gino
 * Created on 2018/10/15
 */
@Slf4j
@Component
public class Consumer {
    @KafkaListener(topics = "${app.topic}")
    public void receive(String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        // 模拟其中一个消费者处理时间较长
        if (partitions.get(0)==0) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        log.info("receive message:{}, partition_id:{}, topic:{}, offsets:{}", message, partitions.get(0), topics.get(0), offsets.get(0));
    }
}
