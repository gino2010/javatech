package com.mqtest.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class ConsumerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Value("${app.queue}")
    private String queueName;
    @Value("${app.broadone}")
    private String broadone;

    @Value("${app.broadtwo}")
    private String broadtwo;

    @Bean
    public Queue queue(){
        return new Queue(queueName);
    }

    @Bean
    public Queue queueBroadone(){
        return new Queue(broadone, false, false, true);
    }

    @Bean
    public Queue queueBroadtwo(){
        return new Queue(broadtwo, false, false, true);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("rabbitmq consumer server start...");
    }
}
