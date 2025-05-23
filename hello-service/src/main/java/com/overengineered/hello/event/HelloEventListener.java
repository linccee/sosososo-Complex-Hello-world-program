package com.overengineered.hello.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Listener for HelloGeneratedEvent.
 * Demonstrates the Observer Pattern by listening to events and performing actions.
 */
@Component
@Slf4j
public class HelloEventListener {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault());

    public HelloEventListener(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Asynchronously log the event when a hello greeting is generated.
     *
     * @param event The HelloGeneratedEvent
     */
    @EventListener
    @Async
    public void handleHelloGeneratedEvent(HelloGeneratedEvent event) {
        log.info("Hello event received: {} in language {} using strategy {} at {}",
                event.getGreeting(),
                event.getLanguage(),
                event.getStrategyUsed(),
                formatter.format(Instant.ofEpochMilli(event.getTimestamp())));
        
        // Send event to Kafka topic
        try {
            String message = String.format("{\"greeting\":\"%s\",\"language\":\"%s\",\"strategy\":\"%s\",\"timestamp\":%d}",
                    event.getGreeting(), 
                    event.getLanguage(), 
                    event.getStrategyUsed(),
                    event.getTimestamp());
            
            kafkaTemplate.send("hello-events", message);
            log.debug("Event sent to Kafka topic 'hello-events'");
        } catch (Exception e) {
            log.error("Failed to send event to Kafka", e);
        }
    }
}
