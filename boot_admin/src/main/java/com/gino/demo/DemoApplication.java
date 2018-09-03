package com.gino.demo;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author gino
 */
@Slf4j
@EnableAdminServer
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Value("${management.server.port}")
    private int actuatorPort;
    @Value("${server.port}")
    private int webPort;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Actuator on {}, Web Server on {}", actuatorPort, webPort);
        log.warn("-- This is spring boot admin demo, test warning log. --");
    }
}
