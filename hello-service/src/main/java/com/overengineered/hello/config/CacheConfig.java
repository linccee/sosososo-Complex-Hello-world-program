package com.overengineered.hello.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for cache management.
 * This is completely unnecessary for a simple hello world application.
 */
@Configuration
@EnableCaching
@Slf4j
public class CacheConfig {

    /**
     * Configure the cache manager.
     *
     * @return The cache manager
     */
    @Bean
    public CacheManager cacheManager() {
        log.info("Configuring cache manager");
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager("helloGreetings");
        
        // Set cache specification
        cacheManager.setCacheNames(java.util.Arrays.asList("helloGreetings"));
        
        return cacheManager;
    }
}
