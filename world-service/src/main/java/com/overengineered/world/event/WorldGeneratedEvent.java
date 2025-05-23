package com.overengineered.world.event;

import com.overengineered.world.document.WorldEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event that is published when a world text is generated.
 * This demonstrates the Observer Pattern via Spring's event system.
 */
@Getter
public class WorldGeneratedEvent extends ApplicationEvent {

    private final String text;
    private final String language;
    private final WorldEntity.PlanetType planetType;
    private final WorldEntity.GeographicalScope scope;
    private final String strategyUsed;
    private final long timestamp;

    /**
     * Create a new WorldGeneratedEvent.
     *
     * @param source The object on which the event initially occurred
     * @param text The generated world text
     * @param language The language of the text
     * @param planetType The planet type
     * @param scope The geographical scope
     * @param strategyUsed The strategy used to generate the text
     */
    public WorldGeneratedEvent(Object source, String text, String language, 
                              WorldEntity.PlanetType planetType, 
                              WorldEntity.GeographicalScope scope,
                              String strategyUsed) {
        super(source);
        this.text = text;
        this.language = language;
        this.planetType = planetType;
        this.scope = scope;
        this.strategyUsed = strategyUsed;
        this.timestamp = System.currentTimeMillis();
    }
}
