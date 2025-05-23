package com.overengineered.world.service;

import com.overengineered.world.document.WorldEntity;
import com.overengineered.world.dto.WorldEntityDto;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing world entities.
 * Defines methods for retrieving, creating, updating and generating world texts.
 */
public interface WorldService {

    /**
     * Get all world entities.
     *
     * @return List of all world entity DTOs
     */
    List<WorldEntityDto> getAllWorldEntities();
    
    /**
     * Get a world entity by ID.
     *
     * @param id The ID of the entity to retrieve
     * @return Optional containing the entity if found
     */
    Optional<WorldEntityDto> getWorldEntityById(String id);
    
    /**
     * Get a world entity by UUID.
     *
     * @param uuid The UUID of the entity to retrieve
     * @return Optional containing the entity if found
     */
    Optional<WorldEntityDto> getWorldEntityByUuid(String uuid);
    
    /**
     * Save a new world entity.
     *
     * @param entityDto The entity DTO to save
     * @return The saved entity DTO with generated ID
     */
    WorldEntityDto saveWorldEntity(WorldEntityDto entityDto);
    
    /**
     * Update an existing world entity.
     *
     * @param id The ID of the entity to update
     * @param entityDto The updated entity DTO
     * @return The updated entity DTO
     */
    WorldEntityDto updateWorldEntity(String id, WorldEntityDto entityDto);
    
    /**
     * Delete a world entity by ID.
     *
     * @param id The ID of the entity to delete
     */
    void deleteWorldEntity(String id);
    
    /**
     * Generate a world text based on language, planet type, and geographical scope.
     *
     * @param language The language code
     * @param planetType The planet type
     * @param scope The geographical scope
     * @return The generated world text
     */
    String generateWorld(String language, WorldEntity.PlanetType planetType, WorldEntity.GeographicalScope scope);
}
