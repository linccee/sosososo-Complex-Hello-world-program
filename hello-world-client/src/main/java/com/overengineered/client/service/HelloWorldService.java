package com.overengineered.client.service;

import com.overengineered.client.command.HelloWorldCommand.HelloWorldRequest;
import com.overengineered.client.command.HelloWorldCommand.HelloWorldResponse;

/**
 * Service interface for making requests to the Hello World Aggregator Service.
 */
public interface HelloWorldService {

    /**
     * Generate a Hello World message synchronously.
     *
     * @param request The request containing all customization parameters
     * @return The generated Hello World message response
     */
    HelloWorldResponse generateHelloWorld(HelloWorldRequest request);
    
    /**
     * Generate a Hello World message asynchronously.
     *
     * @param request The request containing all customization parameters
     * @param pollIntervalMs The interval in milliseconds to poll for the result
     * @return The generated Hello World message response
     */
    HelloWorldResponse generateHelloWorldAsync(HelloWorldRequest request, long pollIntervalMs);
    
    /**
     * Get a Hello World message by ID.
     *
     * @param id The ID of the message to retrieve
     * @return The Hello World message response
     */
    HelloWorldResponse getHelloWorldById(String id);
}
