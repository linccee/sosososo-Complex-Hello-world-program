package com.overengineered.aggregator.config;

import com.overengineered.aggregator.dto.HelloWorldResponseDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Configuration class for Redis.
 * This configuration is unnecessary for a simple hello world application.
 */
@Configuration
public class RedisConfig {

    /**
     * Configure a RedisTemplate for storing HelloWorldResponseDto objects.
     *
     * @param connectionFactory The Redis connection factory
     * @return The configured RedisTemplate
     */
    @Bean
    public RedisTemplate<String, HelloWorldResponseDto> helloWorldRedisTemplate(
            RedisConnectionFactory connectionFactory) {
        
        RedisTemplate<String, HelloWorldResponseDto> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        
        // Use Jackson serializer for values
        Jackson2JsonRedisSerializer<HelloWorldResponseDto> serializer = 
                new Jackson2JsonRedisSerializer<>(HelloWorldResponseDto.class);
        
        // Use String serializer for keys
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        
        return template;
    }
}
