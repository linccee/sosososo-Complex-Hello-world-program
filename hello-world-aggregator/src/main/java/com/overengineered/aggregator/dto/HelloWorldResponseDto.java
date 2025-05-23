package com.overengineered.aggregator.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.overengineered.aggregator.model.GeographicalScope;
import com.overengineered.aggregator.model.PlanetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for returning a Hello World message.
 * Contains all the information about the generated message.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HelloWorldResponseDto {

    private String id;
    private String message;
    private String helloText;
    private String worldText;
    private String language;
    private Integer formalityLevel;
    private PlanetType planetType;
    private GeographicalScope scope;
    private String delimiter;
    private LocalDateTime generatedAt;
    private Long generationTimeMillis;
    private String requestId;
    
    // Metadata about how the message was generated
    private String helloStrategy;
    private String worldStrategy;
    private Boolean isFromCache;
    private String source;
    
    /**
     * Provide additional methods for clients to access parts of the message
     */
    public String getHelloPartOnly() {
        return helloText;
    }
    
    public String getWorldPartOnly() {
        return worldText;
    }
    
    public String getReversedMessage() {
        return new StringBuilder(message).reverse().toString();
    }
    
    public String getUppercaseMessage() {
        return message.toUpperCase();
    }
    
    public String getLowercaseMessage() {
        return message.toLowerCase();
    }
}
