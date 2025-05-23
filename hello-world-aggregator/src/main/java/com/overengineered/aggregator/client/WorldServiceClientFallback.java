package com.overengineered.aggregator.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Fallback implementation for the WorldServiceClient.
 * This class is used when the circuit breaker opens due to failures in the World service.
 */
@Component
@Slf4j
public class WorldServiceClientFallback implements WorldServiceClient {

    @Override
    public String generateWorld(String language, String planetType, String scope) {
        log.warn("Fallback method invoked for generateWorld with language: {}, planet type: {}, and scope: {}", 
                language, planetType, scope);
        return "World (fallback)";
    }
}
