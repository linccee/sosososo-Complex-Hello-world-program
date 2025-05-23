package com.overengineered.aggregator.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Fallback implementation for the HelloServiceClient.
 * This class is used when the circuit breaker opens due to failures in the Hello service.
 */
@Component
@Slf4j
public class HelloServiceClientFallback implements HelloServiceClient {

    @Override
    public String generateHello(String language, int formalityLevel) {
        log.warn("Fallback method invoked for generateHello with language: {} and formality level: {}", 
                language, formalityLevel);
        return "Hello (fallback)";
    }
}
