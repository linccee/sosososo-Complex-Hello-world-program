package com.overengineered.world.exception;

/**
 * Exception thrown when a requested world entity is not found.
 */
public class WorldEntityNotFoundException extends RuntimeException {
    
    public WorldEntityNotFoundException(String message) {
        super(message);
    }
    
    public WorldEntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
