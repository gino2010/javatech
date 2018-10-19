package com.mqtest.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class ProducerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Value("${app.queue}")
    private String queueName;

    @Value("${app.exchange}")
    private String broadcast;

    @Value("${app.broadone}")
    private String broadone;

    @Value("${app.broadtwo}")
    private String broadtwo;

    @Bean
    public Queue queue() {
        return new Queue(queueName);
    }

    @Bean
    public Queue queueBroadone() {
        return new Queue(broadone, false, false, true);
    }

    @Bean
    public Queue queueBroadtwo() {
        return new Queue(broadtwo, false, false, true);
    }

    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange(broadcast, false, true);
    }

    @Bean
    Binding binding1(Queue queueBroadone, FanoutExchange exchange) {
        return BindingBuilder.bind(queueBroadone).to(exchange);
    }

    @Bean
    Binding binding2(Queue queueBroadtwo, FanoutExchange exchange) {
        return BindingBuilder.bind(queueBroadtwo).to(exchange);
    }

    @Autowired
    Producer producer;

    @Override
    public void run(String... args) throws Exception {
        log.info("rabbitmq producer server start...");
    }
}
