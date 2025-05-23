package com.overengineered.client.exception;

/**
 * Exception thrown when an error occurs in the Hello World client.
 */
public class HelloWorldClientException extends RuntimeException {

    /**
     * Create a new exception with a message.
     *
     * @param message The error message
     */
    public HelloWorldClientException(String message) {
        super(message);
    }

    /**
     * Create a new exception with a message and cause.
     *
     * @param message The error message
     * @param cause The cause of the error
     */
    public HelloWorldClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
