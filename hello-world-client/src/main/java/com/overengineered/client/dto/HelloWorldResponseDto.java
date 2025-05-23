package com.overengineered.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Data Transfer Object for Hello World responses from the aggregator service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelloWorldResponseDto {
    
    private String messageId;
    private String message;
    private String helloText;
    private String worldText;
    private String language;
    private Integer formalityLevel;
    private String planetType;
    private String scope;
    private String delimiter;
    private LocalDateTime generatedAt;
    private String requestId;
    private String clientId;
    private String correlationId;
    
    // Strategy information
    private String helloStrategy;
    private String worldStrategy;
    private String aggregationStrategy;
    
    // Performance metrics
    private Long helloServiceTimeMillis;
    private Long worldServiceTimeMillis;
    private Long aggregationTimeMillis;
    private Long totalProcessingTimeMillis;
    
    // Cache information
    private Boolean helloFromCache;
    private Boolean worldFromCache;
    
    // Metadata
    private Map<String, String> metadata;
    
    // Circuit breaker status
    private String helloServiceStatus;
    private String worldServiceStatus;
    
    // History of previously generated messages (for returning users)
    private List<String> previousMessages;
}
