package com.overengineered.aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The entry point for the Hello World Aggregator Service.
 * This complex microservice is responsible for the sophisticated task of combining the "Hello" and "World"
 * parts of the "Hello World" message.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableAsync
@EnableScheduling
@EnableCaching
public class AggregatorServiceApplication {

    /**
     * The main method that starts the Spring Boot application for the Aggregator Service.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(AggregatorServiceApplication.class, args);
    }
}
