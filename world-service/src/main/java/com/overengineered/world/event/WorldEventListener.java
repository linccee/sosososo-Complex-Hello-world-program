package com.overengineered.world.event;

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
 * Listener for WorldGeneratedEvent.
 * Demonstrates the Observer Pattern by listening to events and performing actions.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class WorldEventListener {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault());

    /**
     * Asynchronously log the event when a world text is generated.
     *
     * @param event The WorldGeneratedEvent
     */
    @EventListener
    @Async
    public void handleWorldGeneratedEvent(WorldGeneratedEvent event) {
        log.info("World event received: {} in language {} for planet {} with scope {} using strategy {} at {}",
                event.getText(),
                event.getLanguage(),
                event.getPlanetType(),
                event.getScope(),
                event.getStrategyUsed(),
                formatter.format(Instant.ofEpochMilli(event.getTimestamp())));
        
        // Send event to Kafka topic
        try {
            String message = String.format("{\"text\":\"%s\",\"language\":\"%s\",\"planetType\":\"%s\",\"scope\":\"%s\",\"strategy\":\"%s\",\"timestamp\":%d}",
                    event.getText(), 
                    event.getLanguage(), 
                    event.getPlanetType(),
                    event.getScope(),
                    event.getStrategyUsed(),
                    event.getTimestamp());
            
            kafkaTemplate.send("world-events", message);
            log.debug("Event sent to Kafka topic 'world-events'");
        } catch (Exception e) {
            log.error("Failed to send event to Kafka", e);
        }
    }
}
