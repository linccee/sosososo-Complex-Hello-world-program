package com.overengineered.aggregator.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Listener for HelloWorldGeneratedEvent.
 * Demonstrates the Observer Pattern by listening to events and performing actions.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class HelloWorldEventListener {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault());

    /**
     * Asynchronously handle the event when a hello world message is generated.
     *
     * @param event The HelloWorldGeneratedEvent
     */
    @EventListener
    @Async
    public void handleHelloWorldGeneratedEvent(HelloWorldGeneratedEvent event) {
        log.info("Hello World event received at {}: {}",
                formatter.format(Instant.ofEpochMilli(event.getTimestamp())),
                event.getResponse().getMessage());
        
        // Send event to Kafka topic
        try {
            String message = objectMapper.writeValueAsString(event.getResponse());
            kafkaTemplate.send("hello-world-events", message);
            log.debug("Event sent to Kafka topic 'hello-world-events'");
        } catch (Exception e) {
            log.error("Failed to send event to Kafka", e);
        }
        
        // Other potential actions:
        // - Store in database
        // - Send notification
        // - Update cache
        // - Trigger other business logic
    }
}
