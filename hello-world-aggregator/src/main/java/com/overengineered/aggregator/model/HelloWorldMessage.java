package com.overengineered.aggregator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * A model representing the complete Hello World message.
 * This combines the Hello and World parts into a single, overly complex entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelloWorldMessage {

    private String id;
    private String helloText;
    private String worldText;
    private String language;
    private int formalityLevel;
    private PlanetType planetType;
    private GeographicalScope scope;
    private String delimiter;
    private LocalDateTime generatedAt;
    private String requestId;
    private String source;
    private long generationTimeMillis;
    
    /**
     * Get the complete message by combining the hello and world parts.
     *
     * @return The complete hello world message
     */
    public String getCompleteMessage() {
        return helloText + delimiter + worldText;
    }
    
    /**
     * Static factory method to create a new HelloWorldMessage with default values.
     *
     * @return A new HelloWorldMessage with default values
     */
    public static HelloWorldMessage createDefault() {
        return HelloWorldMessage.builder()
                .id(UUID.randomUUID().toString())
                .helloText("Hello")
                .worldText("World")
                .language("en")
                .formalityLevel(3)
                .planetType(PlanetType.EARTH)
                .scope(GeographicalScope.GLOBAL)
                .delimiter(" ")
                .generatedAt(LocalDateTime.now())
                .requestId(UUID.randomUUID().toString())
                .source("DEFAULT")
                .generationTimeMillis(0)
                .build();
    }
}
