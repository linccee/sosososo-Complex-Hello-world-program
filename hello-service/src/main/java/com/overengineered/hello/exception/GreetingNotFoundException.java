package com.overengineered.hello.exception;

/**
 * Exception thrown when a requested greeting is not found.
 */
public class GreetingNotFoundException extends RuntimeException {
    
    public GreetingNotFoundException(String message) {
        super(message);
    }
    
    public GreetingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
