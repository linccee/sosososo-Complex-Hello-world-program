package com.overengineered.client;

import com.overengineered.client.command.HelloWorldCommand;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import picocli.CommandLine;
import picocli.CommandLine.IFactory;

/**
 * The entry point for the Hello World Client application.
 * This complex command-line client is responsible for making requests to the Hello World Aggregator Service.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class HelloWorldClientApplication implements Runnable {

    private final HelloWorldCommand helloWorldCommand;
    private final IFactory factory;

    public HelloWorldClientApplication(HelloWorldCommand helloWorldCommand, IFactory factory) {
        this.helloWorldCommand = helloWorldCommand;
        this.factory = factory;
    }

    @Override
    public void run() {
        int exitCode = new CommandLine(helloWorldCommand, factory).execute(new String[0]);
        System.exit(exitCode);
    }

    /**
     * The main method that starts the Spring Boot application for the Client.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(HelloWorldClientApplication.class, args);
    }
}
