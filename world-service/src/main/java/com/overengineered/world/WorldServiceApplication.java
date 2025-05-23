package com.overengineered.world;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The entry point for the World Service.
 * This complex microservice is responsible for the sophisticated task of providing the "World" part
 * of the "Hello World" message.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableMongoRepositories
@EnableAsync
@EnableScheduling
@EnableKafka
@EnableCaching
public class WorldServiceApplication {

    /**
     * The main method that starts the Spring Boot application for the World Service.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(WorldServiceApplication.class, args);
    }
}
