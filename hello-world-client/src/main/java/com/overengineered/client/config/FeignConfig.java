package com.overengineered.client.config;

import feign.Request;
import feign.Retryer;
import feign.slf4j.Slf4jLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Configuration for Feign clients.
 */
@Configuration
@EnableFeignClients(basePackages = "com.overengineered.client.client")
@RequiredArgsConstructor
public class FeignConfig {
    
    private final ClientProperties properties;

    /**
     * Configures the request options for Feign clients.
     *
     * @return Request.Options instance
     */
    @Bean
    public Request.Options requestOptions() {
        return new Request.Options(
                properties.getRequestTimeoutMs(), TimeUnit.MILLISECONDS,
                properties.getRequestTimeoutMs(), TimeUnit.MILLISECONDS,
                true);
    }

    /**
     * Configures the retry behavior for Feign clients.
     *
     * @return Retryer instance
     */
    @Bean
    public Retryer retryer() {
        ClientProperties.RetryConfig retryConfig = properties.getRetry();
        
        return new Retryer.Default(
                retryConfig.getBackoffMs(),
                retryConfig.getMaxBackoffMs(),
                retryConfig.getMaxAttempts()
        );
    }

    /**
     * Configures logging for Feign clients.
     *
     * @return Logger instance
     */
    @Bean
    public feign.Logger logger() {
        return new Slf4jLogger();
    }
}
