package com.overengineered.client.client;

import com.overengineered.client.dto.AsyncResponseDto;
import com.overengineered.client.dto.HelloWorldRequestDto;
import com.overengineered.client.dto.HelloWorldResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Fallback implementation for the AggregatorServiceClient.
 * This class provides default responses when the aggregator service is unavailable.
 */
@Component
@Slf4j
public class AggregatorServiceClientFallback implements AggregatorServiceClient {

    @Override
    public HelloWorldResponseDto generateHelloWorld(HelloWorldRequestDto requestDto) {
        log.warn("Fallback: generating default Hello World response");
        return createFallbackResponse(requestDto);
    }

    @Override
    public AsyncResponseDto generateHelloWorldAsync(HelloWorldRequestDto requestDto) {
        log.warn("Fallback: generating default async response");
        return AsyncResponseDto.builder()
                .messageId(UUID.randomUUID().toString())
                .status("FALLBACK")
                .requestId(requestDto.getRequestId())
                .estimatedCompletionTime("N/A")
                .position(0)
                .pollUrl("N/A")
                .build();
    }

    @Override
    public HelloWorldResponseDto getHelloWorldById(String id) {
        log.warn("Fallback: generating default Hello World response for ID: {}", id);
        return HelloWorldResponseDto.builder()
                .messageId(id)
                .message("Hello World (Fallback Response)")
                .helloText("Hello")
                .worldText("World")
                .language("en")
                .formalityLevel(3)
                .planetType("EARTH")
                .scope("GLOBAL")
                .delimiter(" ")
                .generatedAt(LocalDateTime.now())
                .helloStrategy("FallbackStrategy")
                .worldStrategy("FallbackStrategy")
                .aggregationStrategy("FallbackStrategy")
                .helloServiceStatus("DOWN")
                .worldServiceStatus("DOWN")
                .build();
    }

    @Override
    public AsyncResponseDto checkAsyncStatus(String id) {
        log.warn("Fallback: generating default async status for ID: {}", id);
        return AsyncResponseDto.builder()
                .messageId(id)
                .status("FALLBACK")
                .requestId("N/A")
                .estimatedCompletionTime("N/A")
                .position(0)
                .pollUrl("N/A")
                .build();
    }

    /**
     * Create a fallback response based on the request.
     *
     * @param requestDto The original request
     * @return A fallback response
     */
    private HelloWorldResponseDto createFallbackResponse(HelloWorldRequestDto requestDto) {
        String helloText = "Hello";
        String worldText = "World";
        String delimiter = requestDto.getDelimiter() != null ? requestDto.getDelimiter() : " ";
        
        // Apply basic transformations
        if (Boolean.TRUE.equals(requestDto.getUppercase())) {
            helloText = helloText.toUpperCase();
            worldText = worldText.toUpperCase();
        }
        
        if (Boolean.TRUE.equals(requestDto.getReversed())) {
            helloText = new StringBuilder(helloText).reverse().toString();
            worldText = new StringBuilder(worldText).reverse().toString();
        }
        
        String message = helloText + delimiter + worldText + " (Fallback Response)";
        
        return HelloWorldResponseDto.builder()
                .messageId(UUID.randomUUID().toString())
                .message(message)
                .helloText(helloText)
                .worldText(worldText)
                .language(requestDto.getLanguage())
                .formalityLevel(requestDto.getFormalityLevel())
                .planetType(requestDto.getPlanetType())
                .scope(requestDto.getScope())
                .delimiter(delimiter)
                .generatedAt(LocalDateTime.now())
                .requestId(requestDto.getRequestId())
                .clientId(requestDto.getClientId())
                .correlationId(requestDto.getCorrelationId())
                .helloStrategy("FallbackStrategy")
                .worldStrategy("FallbackStrategy")
                .aggregationStrategy("FallbackStrategy")
                .helloServiceStatus("DOWN")
                .worldServiceStatus("DOWN")
                .build();
    }
}
