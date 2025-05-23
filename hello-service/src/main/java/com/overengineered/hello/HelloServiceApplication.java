package com.overengineered.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The entry point for the Hello Service.
 * This complex microservice is responsible for the sophisticated task of providing the "Hello" part
 * of the "Hello World" message.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories
@EnableAsync
@EnableScheduling
@EnableKafka
public class HelloServiceApplication {

    /**
     * The main method that starts the Spring Boot application for the Hello Service.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(HelloServiceApplication.class, args);
    }
}
