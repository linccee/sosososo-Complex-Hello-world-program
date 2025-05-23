package com.overengineered.aggregator.event;

import com.overengineered.aggregator.dto.HelloWorldResponseDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event that is published when a Hello World message is generated.
 * This demonstrates the Observer Pattern via Spring's event system.
 */
@Getter
public class HelloWorldGeneratedEvent extends ApplicationEvent {

    private final HelloWorldResponseDto response;
    private final long timestamp;

    /**
     * Create a new HelloWorldGeneratedEvent.
     *
     * @param source The object on which the event initially occurred
     * @param response The generated Hello World response
     */
    public HelloWorldGeneratedEvent(Object source, HelloWorldResponseDto response) {
        super(source);
        this.response = response;
        this.timestamp = System.currentTimeMillis();
    }
}
