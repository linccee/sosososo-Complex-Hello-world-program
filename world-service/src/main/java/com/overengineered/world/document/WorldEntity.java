package com.overengineered.world.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * MongoDB document representing a "World" entity.
 * Instead of a simple string, we've made it a full-fledged document with numerous properties.
 */
@Document(collection = "world_entities")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorldEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String uuid = UUID.randomUUID().toString();

    @Indexed
    private String language;

    private String text;

    private Boolean isActive;

    private Integer priority;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Version
    private Long version;

    private String description;

    private PlanetType planetType;

    private Continent continent;
    
    /**
     * Geographical scope of the entity.
     */
    private GeographicalScope geographicalScope;

    /**
     * Enum representing different planet types.
     */
    public enum PlanetType {
        EARTH, MARS, VENUS, JUPITER, SATURN, NEPTUNE, URANUS, MERCURY, PLUTO
    }

    /**
     * Enum representing major continents.
     */
    public enum Continent {
        AFRICA, ANTARCTICA, ASIA, AUSTRALIA, EUROPE, NORTH_AMERICA, SOUTH_AMERICA
    }
    
    /**
     * Enum representing geographical scopes.
     */
    public enum GeographicalScope {
        GLOBAL, CONTINENTAL, REGIONAL, NATIONAL, LOCAL
    }
}
