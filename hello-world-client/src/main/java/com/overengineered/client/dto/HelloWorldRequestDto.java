package com.overengineered.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Hello World requests to the aggregator service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelloWorldRequestDto {
    
    private String language;
    private Integer formalityLevel;
    private String planetType;
    private String scope;
    private String delimiter;
    private Boolean uppercase;
    private Boolean reversed;
    private Boolean encrypted;
    private Boolean async;
    private String requestId;
    private String clientId;
    private String correlationId;
    
    // Formatting preferences
    private Boolean includePunctuation;
    private Boolean useColorEmphasis;
    private String fontStyle;
    private Integer fontSize;
    
    // Performance metrics
    private Boolean collectPerformanceMetrics;
    private Boolean enableTracing;
}
