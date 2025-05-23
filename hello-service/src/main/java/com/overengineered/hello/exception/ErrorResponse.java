package com.overengineered.hello.exception;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Standardized error response format for the API.
 */
@Getter
public class ErrorResponse {
    private final String timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;
    
    public ErrorResponse(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now().toString();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
