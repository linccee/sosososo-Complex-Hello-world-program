package com.overengineered.world.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.overengineered.world.document.WorldEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for WorldEntity document.
 * Contains all the information needed to exchange world entity data between layers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorldEntityDto {

    private String id;
    private String uuid;
    
    @NotBlank(message = "Language is required")
    private String language;
    
    @NotBlank(message = "Text is required")
    private String text;
    
    @NotNull(message = "Active status is required")
    private Boolean isActive;
    
    private Integer priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long version;
    private String description;
    
    @NotNull(message = "Planet type is required")
    private WorldEntity.PlanetType planetType;
    
    private WorldEntity.Continent continent;
    private WorldEntity.GeographicalScope geographicalScope;
    
    // Metadata for tracking and auditing
    private String createdBy;
    private String lastModifiedBy;
    private String source;
}
