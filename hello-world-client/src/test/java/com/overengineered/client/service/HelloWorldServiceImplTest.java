package com.overengineered.client.service;

import com.overengineered.client.client.AggregatorServiceClient;
import com.overengineered.client.command.HelloWorldCommand.HelloWorldRequest;
import com.overengineered.client.command.HelloWorldCommand.HelloWorldResponse;
import com.overengineered.client.config.ClientProperties;
import com.overengineered.client.dto.AsyncResponseDto;
import com.overengineered.client.dto.HelloWorldRequestDto;
import com.overengineered.client.dto.HelloWorldResponseDto;
import com.overengineered.client.mapper.HelloWorldMapper;
import com.overengineered.client.util.RetryUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HelloWorldServiceImplTest {

    @Mock
    private AggregatorServiceClient aggregatorClient;

    @Mock
    private HelloWorldMapper mapper;

    @Mock
    private ClientProperties properties;

    @Mock
    private RetryUtil retryUtil;

    private HelloWorldServiceImpl helloWorldService;

    @BeforeEach
    void setUp() {
        ClientProperties.RetryConfig retryConfig = new ClientProperties.RetryConfig();
        when(properties.getRetry()).thenReturn(retryConfig);
        when(properties.getMaxPollAttempts()).thenReturn(10);
        
        helloWorldService = new HelloWorldServiceImpl(aggregatorClient, mapper, properties, retryUtil);
    }

    @Test
    void generateHelloWorld_shouldCallAggregatorAndMapResponse() {
        // Arrange
        HelloWorldRequest request = HelloWorldRequest.builder()
                .language("en")
                .formalityLevel(3)
                .planetType("EARTH")
                .scope("GLOBAL")
                .delimiter(" ")
                .build();
        
        HelloWorldRequestDto requestDto = HelloWorldRequestDto.builder()
                .language("en")
                .formalityLevel(3)
                .planetType("EARTH")
                .scope("GLOBAL")
                .delimiter(" ")
                .build();
        
        HelloWorldResponseDto responseDto = HelloWorldResponseDto.builder()
                .messageId(UUID.randomUUID().toString())
                .message("Hello World")
                .helloText("Hello")
                .worldText("World")
                .language("en")
                .formalityLevel(3)
                .planetType("EARTH")
                .scope("GLOBAL")
                .delimiter(" ")
                .generatedAt(LocalDateTime.now())
                .build();
        
        HelloWorldResponse response = HelloWorldResponse.builder()
                .id(responseDto.getMessageId())
                .message("Hello World")
                .helloText("Hello")
                .worldText("World")
                .language("en")
                .formalityLevel(3)
                .planetType("EARTH")
                .scope("GLOBAL")
                .delimiter(" ")
                .generatedAt(responseDto.getGeneratedAt().toString())
                .build();
        
        when(mapper.toRequestDto(request)).thenReturn(requestDto);
        when(aggregatorClient.generateHelloWorld(any(HelloWorldRequestDto.class))).thenReturn(responseDto);
        when(mapper.toResponse(responseDto)).thenReturn(response);
        
        // Act
        HelloWorldResponse result = helloWorldService.generateHelloWorld(request);
        
        // Assert
        assertNotNull(result);
        assertEquals("Hello World", result.getMessage());
        assertEquals("Hello", result.getHelloText());
        assertEquals("World", result.getWorldText());
        
        ArgumentCaptor<HelloWorldRequestDto> requestCaptor = ArgumentCaptor.forClass(HelloWorldRequestDto.class);
        verify(aggregatorClient).generateHelloWorld(requestCaptor.capture());
        assertNotNull(requestCaptor.getValue().getRequestId());
    }

    @Test
    void generateHelloWorldAsync_shouldCallAggregatorAsyncAndPollForResult() {
        // Arrange
        HelloWorldRequest request = HelloWorldRequest.builder()
                .language("en")
                .formalityLevel(3)
                .planetType("EARTH")
                .scope("GLOBAL")
                .delimiter(" ")
                .build();
        
        HelloWorldRequestDto requestDto = HelloWorldRequestDto.builder()
                .language("en")
                .formalityLevel(3)
                .planetType("EARTH")
                .scope("GLOBAL")
                .delimiter(" ")
                .build();
        
        AsyncResponseDto asyncResponseDto = AsyncResponseDto.builder()
                .messageId(UUID.randomUUID().toString())
                .status("PROCESSING")
                .build();
        
        HelloWorldResponseDto responseDto = HelloWorldResponseDto.builder()
                .messageId(asyncResponseDto.getMessageId())
                .message("Hello World")
                .helloText("Hello")
                .worldText("World")
                .language("en")
                .formalityLevel(3)
                .planetType("EARTH")
                .scope("GLOBAL")
                .delimiter(" ")
                .generatedAt(LocalDateTime.now())
                .build();
        
        HelloWorldResponse response = HelloWorldResponse.builder()
                .id(responseDto.getMessageId())
                .message("Hello World")
                .helloText("Hello")
                .worldText("World")
                .language("en")
                .formalityLevel(3)
                .planetType("EARTH")
                .scope("GLOBAL")
                .delimiter(" ")
                .generatedAt(responseDto.getGeneratedAt().toString())
                .build();
        
        when(mapper.toRequestDto(request)).thenReturn(requestDto);
        when(aggregatorClient.generateHelloWorldAsync(any(HelloWorldRequestDto.class))).thenReturn(asyncResponseDto);
        when(retryUtil.retryUntilResult(any(), anyLong(), anyLong(), anyString())).thenReturn(responseDto);
        when(mapper.toResponse(responseDto)).thenReturn(response);
        
        // Act
        HelloWorldResponse result = helloWorldService.generateHelloWorldAsync(request, 500);
        
        // Assert
        assertNotNull(result);
        assertEquals("Hello World", result.getMessage());
        assertEquals("Hello", result.getHelloText());
        assertEquals("World", result.getWorldText());
        
        ArgumentCaptor<HelloWorldRequestDto> requestCaptor = ArgumentCaptor.forClass(HelloWorldRequestDto.class);
        verify(aggregatorClient).generateHelloWorldAsync(requestCaptor.capture());
        assertEquals(true, requestCaptor.getValue().getAsync());
        assertNotNull(requestCaptor.getValue().getRequestId());
    }

    @Test
    void getHelloWorldById_shouldCallAggregatorAndMapResponse() {
        // Arrange
        String id = UUID.randomUUID().toString();
        
        HelloWorldResponseDto responseDto = HelloWorldResponseDto.builder()
                .messageId(id)
                .message("Hello World")
                .helloText("Hello")
                .worldText("World")
                .language("en")
                .formalityLevel(3)
                .planetType("EARTH")
                .scope("GLOBAL")
                .delimiter(" ")
                .generatedAt(LocalDateTime.now())
                .build();
        
        HelloWorldResponse response = HelloWorldResponse.builder()
                .id(id)
                .message("Hello World")
                .helloText("Hello")
                .worldText("World")
                .language("en")
                .formalityLevel(3)
                .planetType("EARTH")
                .scope("GLOBAL")
                .delimiter(" ")
                .generatedAt(responseDto.getGeneratedAt().toString())
                .build();
        
        when(aggregatorClient.getHelloWorldById(id)).thenReturn(responseDto);
        when(mapper.toResponse(responseDto)).thenReturn(response);
        
        // Act
        HelloWorldResponse result = helloWorldService.getHelloWorldById(id);
        
        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Hello World", result.getMessage());
        assertEquals("Hello", result.getHelloText());
        assertEquals("World", result.getWorldText());
        
        verify(aggregatorClient, times(1)).getHelloWorldById(id);
    }
}
