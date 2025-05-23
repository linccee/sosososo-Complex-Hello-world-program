package com.overengineered.hello.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event that is published when a hello greeting is generated.
 * This demonstrates the Observer Pattern via Spring's event system.
 */
@Getter
public class HelloGeneratedEvent extends ApplicationEvent {

    private final String greeting;
    private final String language;
    private final String strategyUsed;
    private final long timestamp;

    /**
     * Create a new HelloGeneratedEvent.
     *
     * @param source The object on which the event initially occurred
     * @param greeting The generated greeting
     * @param language The language of the greeting
     * @param strategyUsed The strategy used to generate the greeting
     */
    public HelloGeneratedEvent(Object source, String greeting, String language, String strategyUsed) {
        super(source);
        this.greeting = greeting;
        this.language = language;
        this.strategyUsed = strategyUsed;
        this.timestamp = System.currentTimeMillis();
    }
}
