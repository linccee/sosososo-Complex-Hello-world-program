package com.overengineered.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for asynchronous Hello World response from the aggregator service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsyncResponseDto {
    
    private String messageId;
    private String status;
    private String requestId;
    private String estimatedCompletionTime;
    private Integer position;
    private String pollUrl;
}
