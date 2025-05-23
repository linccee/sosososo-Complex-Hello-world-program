package com.overengineered.client.client;

import com.overengineered.client.dto.AsyncResponseDto;
import com.overengineered.client.dto.HelloWorldRequestDto;
import com.overengineered.client.dto.HelloWorldResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Feign client for communicating with the Hello World Aggregator Service.
 */
@FeignClient(name = "hello-world-aggregator", 
             path = "/api/v1/hello-world",
             fallback = AggregatorServiceClientFallback.class)
public interface AggregatorServiceClient {

    /**
     * Generate a Hello World message synchronously.
     *
     * @param requestDto The request DTO
     * @return The response DTO
     */
    @PostMapping
    HelloWorldResponseDto generateHelloWorld(@RequestBody HelloWorldRequestDto requestDto);

    /**
     * Generate a Hello World message asynchronously.
     *
     * @param requestDto The request DTO
     * @return Asynchronous response with a message ID
     */
    @PostMapping("/async")
    AsyncResponseDto generateHelloWorldAsync(@RequestBody HelloWorldRequestDto requestDto);

    /**
     * Get a Hello World message by ID.
     *
     * @param id The ID of the message to retrieve
     * @return The response DTO
     */
    @GetMapping("/{id}")
    HelloWorldResponseDto getHelloWorldById(@PathVariable("id") String id);

    /**
     * Check the status of an asynchronous request.
     *
     * @param id The ID of the message to check
     * @return Asynchronous response with status information
     */
    @GetMapping("/{id}/status")
    AsyncResponseDto checkAsyncStatus(@PathVariable("id") String id);
}
