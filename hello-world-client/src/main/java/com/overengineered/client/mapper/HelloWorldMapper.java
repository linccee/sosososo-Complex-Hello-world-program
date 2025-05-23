package com.overengineered.client.mapper;

import com.overengineered.client.command.HelloWorldCommand.HelloWorldRequest;
import com.overengineered.client.command.HelloWorldCommand.HelloWorldResponse;
import com.overengineered.client.dto.HelloWorldRequestDto;
import com.overengineered.client.dto.HelloWorldResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

/**
 * Mapper for converting between client objects and DTOs.
 */
@Component
public class HelloWorldMapper {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Value("${client.id}")
    private String clientId;

    /**
     * Convert a client request to a request DTO.
     *
     * @param request The client request
     * @return The request DTO
     */
    public HelloWorldRequestDto toRequestDto(HelloWorldRequest request) {
        String correlationId = UUID.randomUUID().toString();
        
        return HelloWorldRequestDto.builder()
                .language(request.getLanguage())
                .formalityLevel(request.getFormalityLevel())
                .planetType(request.getPlanetType())
                .scope(request.getScope())
                .delimiter(request.getDelimiter())
                .uppercase(request.getUppercase())
                .reversed(request.getReversed())
                .encrypted(request.getEncrypted())
                .clientId(clientId)
                .correlationId(correlationId)
                .includePunctuation(true)
                .useColorEmphasis(false)
                .fontStyle("DEFAULT")
                .fontSize(12)
                .collectPerformanceMetrics(true)
                .enableTracing(true)
                .build();
    }

    /**
     * Convert a response DTO to a client response.
     *
     * @param responseDto The response DTO
     * @return The client response
     */
    public HelloWorldResponse toResponse(HelloWorldResponseDto responseDto) {
        return HelloWorldResponse.builder()
                .id(responseDto.getMessageId())
                .message(responseDto.getMessage())
                .helloText(responseDto.getHelloText())
                .worldText(responseDto.getWorldText())
                .language(responseDto.getLanguage())
                .formalityLevel(responseDto.getFormalityLevel())
                .planetType(responseDto.getPlanetType())
                .scope(responseDto.getScope())
                .delimiter(responseDto.getDelimiter())
                .generatedAt(Optional.ofNullable(responseDto.getGeneratedAt())
                        .map(dt -> dt.format(DATE_FORMATTER))
                        .orElse("N/A"))
                .requestId(responseDto.getRequestId())
                .helloStrategy(responseDto.getHelloStrategy())
                .worldStrategy(responseDto.getWorldStrategy())
                .build();
    }
}
