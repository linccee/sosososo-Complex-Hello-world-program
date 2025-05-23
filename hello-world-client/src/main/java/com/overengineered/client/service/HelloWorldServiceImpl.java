package com.overengineered.client.service;

import com.overengineered.client.client.AggregatorServiceClient;
import com.overengineered.client.command.HelloWorldCommand.HelloWorldRequest;
import com.overengineered.client.command.HelloWorldCommand.HelloWorldResponse;
import com.overengineered.client.config.ClientProperties;
import com.overengineered.client.dto.HelloWorldRequestDto;
import com.overengineered.client.dto.HelloWorldResponseDto;
import com.overengineered.client.exception.HelloWorldClientException;
import com.overengineered.client.mapper.HelloWorldMapper;
import com.overengineered.client.util.RetryUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of the HelloWorldService interface.
 * This service communicates with the Hello World Aggregator Service
 * via a Feign client.
 */
@Service
@Slf4j
public class HelloWorldServiceImpl implements HelloWorldService {

    private final AggregatorServiceClient aggregatorClient;
    private final HelloWorldMapper mapper;
    private final ClientProperties properties;
    private final RetryUtil retryUtil;

    @Autowired
    public HelloWorldServiceImpl(AggregatorServiceClient aggregatorClient,
                                 HelloWorldMapper mapper,
                                 ClientProperties properties,
                                 RetryUtil retryUtil) {
        this.aggregatorClient = aggregatorClient;
        this.mapper = mapper;
        this.properties = properties;
        this.retryUtil = retryUtil;
    }

    @Override
    public HelloWorldResponse generateHelloWorld(HelloWorldRequest request) {
        try {
            log.info("Generating Hello World message synchronously");
            
            // Map client request to DTO
            HelloWorldRequestDto requestDto = mapper.toRequestDto(request);
            requestDto.setRequestId(UUID.randomUUID().toString());
            
            // Call the aggregator service
            long startTime = System.currentTimeMillis();
            HelloWorldResponseDto responseDto = aggregatorClient.generateHelloWorld(requestDto);
            long endTime = System.currentTimeMillis();
            
            // Map response DTO to client response
            HelloWorldResponse response = mapper.toResponse(responseDto);
            response.setGenerationTimeMillis(endTime - startTime);
            
            log.info("Hello World message generated successfully in {} ms", response.getGenerationTimeMillis());
            return response;
        } catch (Exception e) {
            log.error("Error generating Hello World message", e);
            throw new HelloWorldClientException("Failed to generate Hello World message: " + e.getMessage(), e);
        }
    }

    @Override
    public HelloWorldResponse generateHelloWorldAsync(HelloWorldRequest request, long pollIntervalMs) {
        try {
            log.info("Generating Hello World message asynchronously");
            
            // Map client request to DTO
            HelloWorldRequestDto requestDto = mapper.toRequestDto(request);
            requestDto.setRequestId(UUID.randomUUID().toString());
            requestDto.setAsync(true);
            
            // Call the aggregator service to start the async process
            long startTime = System.currentTimeMillis();
            String messageId = aggregatorClient.generateHelloWorldAsync(requestDto).getMessageId();
            log.info("Async request submitted, message ID: {}", messageId);
            
            // Poll for the result
            HelloWorldResponseDto responseDto = retryUtil.retryUntilResult(
                    () -> {
                        log.debug("Polling for result of message ID: {}", messageId);
                        return Optional.ofNullable(aggregatorClient.getHelloWorldById(messageId));
                    },
                    pollIntervalMs,
                    properties.getMaxPollAttempts(),
                    "Timeout waiting for Hello World message"
            );
            
            long endTime = System.currentTimeMillis();
            
            // Map response DTO to client response
            HelloWorldResponse response = mapper.toResponse(responseDto);
            response.setGenerationTimeMillis(endTime - startTime);
            
            log.info("Async Hello World message generated successfully in {} ms", response.getGenerationTimeMillis());
            return response;
        } catch (Exception e) {
            log.error("Error generating async Hello World message", e);
            throw new HelloWorldClientException("Failed to generate async Hello World message: " + e.getMessage(), e);
        }
    }

    @Override
    public HelloWorldResponse getHelloWorldById(String id) {
        try {
            log.info("Retrieving Hello World message with ID: {}", id);
            
            // Call the aggregator service
            HelloWorldResponseDto responseDto = aggregatorClient.getHelloWorldById(id);
            
            // Map response DTO to client response
            HelloWorldResponse response = mapper.toResponse(responseDto);
            
            log.info("Hello World message retrieved successfully");
            return response;
        } catch (Exception e) {
            log.error("Error retrieving Hello World message with ID: {}", id, e);
            throw new HelloWorldClientException("Failed to retrieve Hello World message: " + e.getMessage(), e);
        }
    }
}
