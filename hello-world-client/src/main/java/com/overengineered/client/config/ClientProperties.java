package com.overengineered.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration properties for the client application.
 */
@Component
@ConfigurationProperties(prefix = "client")
@Data
public class ClientProperties {
    
    /**
     * Client identifier used in requests.
     */
    private String id = "hello-world-cli-" + System.getProperty("user.name");
    
    /**
     * Maximum number of attempts to poll for async results.
     */
    private int maxPollAttempts = 60;
    
    /**
     * Default timeout for requests in milliseconds.
     */
    private int requestTimeoutMs = 5000;
    
    /**
     * Whether to enable circuit breakers.
     */
    private boolean enableCircuitBreaker = true;
    
    /**
     * Configuration for connection retries.
     */
    private RetryConfig retry = new RetryConfig();
    
    /**
     * Configuration for fallback behavior.
     */
    private FallbackConfig fallback = new FallbackConfig();
    
    /**
     * Retry configuration.
     */
    @Data
    public static class RetryConfig {
        private int maxAttempts = 3;
        private int backoffMs = 1000;
        private int maxBackoffMs = 10000;
        private double backoffMultiplier = 1.5;
    }
    
    /**
     * Fallback configuration.
     */
    @Data
    public static class FallbackConfig {
        private boolean enabled = true;
        private String defaultLanguage = "en";
        private String defaultMessage = "Hello World (Fallback)";
    }
}
