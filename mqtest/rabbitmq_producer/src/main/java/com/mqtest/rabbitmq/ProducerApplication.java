package com.mqtest.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class ProducerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Value("${app.queue}")
    private String queueName;

    @Bean
    public Queue queue(){
        return new Queue(queueName);
    }

    @Autowired
    Producer producer;

    @Override
    public void run(String... args) throws Exception {
        log.info("rabbitmq producer server start...");
        producer.send("rabbitmq gino test");
    }
}
