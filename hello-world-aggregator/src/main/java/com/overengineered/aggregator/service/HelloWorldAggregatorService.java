package com.overengineered.aggregator.service;

import com.overengineered.aggregator.dto.HelloWorldRequestDto;
import com.overengineered.aggregator.dto.HelloWorldResponseDto;
import com.overengineered.aggregator.model.HelloWorldMessage;

import java.util.Optional;

/**
 * Service interface for aggregating Hello and World services to create a Hello World message.
 */
public interface HelloWorldAggregatorService {

    /**
     * Generate a Hello World message based on the request parameters.
     *
     * @param request The request containing all parameters for customization
     * @return The generated Hello World message response
     */
    HelloWorldResponseDto generateHelloWorld(HelloWorldRequestDto request);
    
    /**
     * Get a previously generated Hello World message by ID.
     *
     * @param id The ID of the message to retrieve
     * @return Optional containing the message if found
     */
    Optional<HelloWorldResponseDto> getHelloWorldById(String id);
    
    /**
     * Generate a Hello World message with default settings.
     *
     * @return The generated Hello World message response
     */
    HelloWorldResponseDto generateDefaultHelloWorld();
    
    /**
     * Generate a Hello World message asynchronously and return immediately.
     * The client can later check for the result using the returned request ID.
     *
     * @param request The request containing all parameters for customization
     * @return The request ID that can be used to retrieve the result later
     */
    String generateHelloWorldAsync(HelloWorldRequestDto request);
    
    /**
     * Check the status of an asynchronous Hello World message generation.
     *
     * @param requestId The request ID returned by generateHelloWorldAsync
     * @return Optional containing the message if generation is complete
     */
    Optional<HelloWorldResponseDto> checkAsyncResult(String requestId);
}
