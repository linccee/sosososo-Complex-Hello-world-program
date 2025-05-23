package com.overengineered.aggregator.service;

import com.overengineered.aggregator.client.HelloServiceClient;
import com.overengineered.aggregator.client.WorldServiceClient;
import com.overengineered.aggregator.dto.HelloWorldRequestDto;
import com.overengineered.aggregator.dto.HelloWorldResponseDto;
import com.overengineered.aggregator.event.HelloWorldGeneratedEvent;
import com.overengineered.aggregator.model.HelloWorldMessage;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CompletableFuture;

/**
 * Implementation of the HelloWorldAggregatorService interface.
 * This class coordinates calls to the Hello and World services to create a complete Hello World message.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class HelloWorldAggregatorServiceImpl implements HelloWorldAggregatorService {

    private final HelloServiceClient helloServiceClient;
    private final WorldServiceClient worldServiceClient;
    private final ApplicationEventPublisher eventPublisher;
    private final RedisTemplate<String, HelloWorldResponseDto> redisTemplate;
    
    // In-memory cache for async results (in a real app, this would use Redis or another distributed cache)
    private final Map<String, CompletableFuture<HelloWorldResponseDto>> asyncResults = new ConcurrentHashMap<>();

    @Override
    @Cacheable(value = "helloWorldMessages", key = "#request.language + '-' + #request.formalityLevel + '-' + #request.planetType + '-' + #request.scope")
    @CircuitBreaker(name = "aggregatorService", fallbackMethod = "fallbackGenerateHelloWorld")
    @Retry(name = "aggregatorService")
    public HelloWorldResponseDto generateHelloWorld(HelloWorldRequestDto request) {
        log.info("Generating Hello World message with request: {}", request);
        long startTime = System.currentTimeMillis();
        
        // Call the Hello service
        String hello = fetchHello(request);
        
        // Call the World service
        String world = fetchWorld(request);
        
        // Apply delimiter
        String delimiter = request.getDelimiter() != null ? request.getDelimiter() : " ";
        
        // Apply transformations if needed
        if (Boolean.TRUE.equals(request.getUppercase())) {
            hello = hello.toUpperCase();
            world = world.toUpperCase();
        }
        
        if (Boolean.TRUE.equals(request.getReversed())) {
            hello = new StringBuilder(hello).reverse().toString();
            world = new StringBuilder(world).reverse().toString();
        }
        
        // Generate the combined message
        String message = hello + delimiter + world;
        
        long endTime = System.currentTimeMillis();
        long generationTime = endTime - startTime;
        
        // Create and populate the response DTO
        HelloWorldResponseDto response = HelloWorldResponseDto.builder()
                .id(UUID.randomUUID().toString())
                .message(message)
                .helloText(hello)
                .worldText(world)
                .language(request.getLanguage())
                .formalityLevel(request.getFormalityLevel())
                .planetType(request.getPlanetType())
                .scope(request.getScope())
                .delimiter(delimiter)
                .generatedAt(LocalDateTime.now())
                .generationTimeMillis(generationTime)
                .requestId(UUID.randomUUID().toString())
                .helloStrategy("REMOTE")
                .worldStrategy("REMOTE")
                .isFromCache(false)
                .source("AGGREGATOR_SERVICE")
                .build();
        
        // Publish event
        eventPublisher.publishEvent(new HelloWorldGeneratedEvent(this, response));
        
        log.info("Generated Hello World message in {}ms: {}", generationTime, response.getMessage());
        return response;
    }

    @Override
    public Optional<HelloWorldResponseDto> getHelloWorldById(String id) {
        log.info("Retrieving Hello World message with ID: {}", id);
        HelloWorldResponseDto response = redisTemplate.opsForValue().get("helloWorld:" + id);
        return Optional.ofNullable(response);
    }

    @Override
    public HelloWorldResponseDto generateDefaultHelloWorld() {
        log.info("Generating default Hello World message");
        return generateHelloWorld(HelloWorldRequestDto.createDefault());
    }

    @Override
    public String generateHelloWorldAsync(HelloWorldRequestDto request) {
        log.info("Starting asynchronous generation of Hello World message with request: {}", request);
        String requestId = UUID.randomUUID().toString();
        
        CompletableFuture<HelloWorldResponseDto> future = CompletableFuture.supplyAsync(() -> 
            generateHelloWorld(request)
        );
        
        asyncResults.put(requestId, future);
        log.debug("Created async task with request ID: {}", requestId);
        
        return requestId;
    }

    @Override
    public Optional<HelloWorldResponseDto> checkAsyncResult(String requestId) {
        log.info("Checking async result for request ID: {}", requestId);
        CompletableFuture<HelloWorldResponseDto> future = asyncResults.get(requestId);
        
        if (future == null) {
            log.warn("No async task found for request ID: {}", requestId);
            return Optional.empty();
        }
        
        if (!future.isDone()) {
            log.debug("Async task is not yet complete for request ID: {}", requestId);
            return Optional.empty();
        }
        
        try {
            HelloWorldResponseDto result = future.get();
            // Remove from the map once retrieved
            asyncResults.remove(requestId);
            return Optional.of(result);
        } catch (Exception e) {
            log.error("Error retrieving async result for request ID: {}", requestId, e);
            asyncResults.remove(requestId);
            return Optional.empty();
        }
    }
    
    /**
     * Fetch the hello part from the Hello service.
     *
     * @param request The request containing language and formality level
     * @return The hello greeting text
     */
    private String fetchHello(HelloWorldRequestDto request) {
        log.debug("Fetching hello part for language: {} and formality level: {}", 
                request.getLanguage(), request.getFormalityLevel());
        return helloServiceClient.generateHello(request.getLanguage(), request.getFormalityLevel());
    }
    
    /**
     * Fetch the world part from the World service.
     *
     * @param request The request containing language, planet type, and scope
     * @return The world text
     */
    private String fetchWorld(HelloWorldRequestDto request) {
        log.debug("Fetching world part for language: {}, planet type: {}, and scope: {}", 
                request.getLanguage(), request.getPlanetType(), request.getScope());
        return worldServiceClient.generateWorld(
                request.getLanguage(), 
                request.getPlanetType().toString(), 
                request.getScope().toString());
    }
    
    /**
     * Fallback method for generateHelloWorld in case of failures.
     *
     * @param request The original request
     * @param e The exception that triggered the fallback
     * @return A fallback Hello World message
     */
    private HelloWorldResponseDto fallbackGenerateHelloWorld(HelloWorldRequestDto request, Exception e) {
        log.warn("Fallback method invoked for generateHelloWorld due to: {}", e.getMessage());
        
        String fallbackMessage = "Hello World (fallback)";
        
        return HelloWorldResponseDto.builder()
                .id(UUID.randomUUID().toString())
                .message(fallbackMessage)
                .helloText("Hello (fallback)")
                .worldText("World (fallback)")
                .language(request.getLanguage())
                .formalityLevel(request.getFormalityLevel())
                .planetType(request.getPlanetType())
                .scope(request.getScope())
                .delimiter(" ")
                .generatedAt(LocalDateTime.now())
                .generationTimeMillis(0L)
                .requestId(UUID.randomUUID().toString())
                .helloStrategy("FALLBACK")
                .worldStrategy("FALLBACK")
                .isFromCache(false)
                .source("FALLBACK")
                .build();
    }
}
