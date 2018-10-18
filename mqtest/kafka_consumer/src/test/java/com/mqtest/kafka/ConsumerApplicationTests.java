package com.mqtest.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsumerApplicationTests {
    @Autowired
    ConsumerFactory consumerFactory;

    @Test
    public void contextLoads() {
    }

    /**
     * 测试手动修改offset，制定消费起点
     */
    @Test
    public void testOffset() {
        TopicPartition topicPartition1 = new TopicPartition("gino.topic", 0);
        KafkaConsumer consumer = (KafkaConsumer) consumerFactory.createConsumer();
        consumer.assign(Collections.singletonList(topicPartition1));
        consumer.seek(topicPartition1, 11);

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
                System.out.println();
            }

        }
    }

}
