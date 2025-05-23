package com.overengineered.world.controller;

import com.overengineered.world.document.WorldEntity;
import com.overengineered.world.dto.WorldEntityDto;
import com.overengineered.world.service.WorldService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * REST controller for managing world entities.
 * This class is absurdly over-engineered for a simple hello world application.
 */
@RestController
@RequestMapping("/api/v1/worlds")
@Validated
@Slf4j
@RequiredArgsConstructor
@Tag(name = "World Entity API", description = "Operations for managing world entities")
public class WorldEntityController {

    private final WorldService worldService;

    /**
     * GET /api/v1/worlds : Get all world entities
     *
     * @return ResponseEntity with the list of world entities
     */
    @GetMapping
    @Operation(summary = "Get all world entities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved world entities"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<WorldEntityDto>> getAllWorldEntities() {
        log.info("REST request to get all world entities");
        List<WorldEntityDto> entities = worldService.getAllWorldEntities();
        return ResponseEntity.ok(entities);
    }

    /**
     * GET /api/v1/worlds/{id} : Get a world entity by ID
     *
     * @param id The ID of the world entity to retrieve
     * @return ResponseEntity with the world entity
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get world entity by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved world entity"),
            @ApiResponse(responseCode = "404", description = "World entity not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<WorldEntityDto> getWorldEntityById(
            @Parameter(description = "ID of the world entity", required = true)
            @PathVariable String id) {
        
        log.info("REST request to get world entity with ID: {}", id);
        return worldService.getWorldEntityById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/v1/worlds : Create a new world entity
     *
     * @param entityDto The world entity to create
     * @return ResponseEntity with the created world entity
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new world entity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created world entity"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<WorldEntityDto> createWorldEntity(
            @Parameter(description = "World entity to create", required = true, schema = @Schema(implementation = WorldEntityDto.class))
            @Valid @RequestBody WorldEntityDto entityDto) {
        
        log.info("REST request to create world entity: {}", entityDto);
        WorldEntityDto savedEntity = worldService.saveWorldEntity(entityDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);
    }

    /**
     * PUT /api/v1/worlds/{id} : Update an existing world entity
     *
     * @param id The ID of the world entity to update
     * @param entityDto The updated world entity
     * @return ResponseEntity with the updated world entity
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update an existing world entity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated world entity"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "World entity not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<WorldEntityDto> updateWorldEntity(
            @Parameter(description = "ID of the world entity to update", required = true)
            @PathVariable String id,
            @Parameter(description = "Updated world entity", required = true, schema = @Schema(implementation = WorldEntityDto.class))
            @Valid @RequestBody WorldEntityDto entityDto) {
        
        log.info("REST request to update world entity with ID: {}", id);
        WorldEntityDto updatedEntity = worldService.updateWorldEntity(id, entityDto);
        return ResponseEntity.ok(updatedEntity);
    }

    /**
     * DELETE /api/v1/worlds/{id} : Delete a world entity
     *
     * @param id The ID of the world entity to delete
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a world entity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted world entity"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "World entity not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteWorldEntity(
            @Parameter(description = "ID of the world entity to delete", required = true)
            @PathVariable String id) {
        
        log.info("REST request to delete world entity with ID: {}", id);
        worldService.deleteWorldEntity(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/v1/worlds/generate : Generate a world text
     *
     * @param language The language code
     * @param planetType The planet type
     * @param scope The geographical scope
     * @return ResponseEntity with the generated world text
     */
    @GetMapping("/generate")
    @Operation(summary = "Generate a world text")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully generated world text"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> generateWorld(
            @Parameter(description = "Language code (e.g., en, fr, es)", required = true)
            @RequestParam @NotBlank String language,
            
            @Parameter(description = "Planet type", required = true)
            @RequestParam WorldEntity.PlanetType planetType,
            
            @Parameter(description = "Geographical scope", required = true)
            @RequestParam WorldEntity.GeographicalScope scope) {
        
        log.info("REST request to generate world in language: {} with planet type: {} and scope: {}", 
                language, planetType, scope);
        String worldText = worldService.generateWorld(language, planetType, scope);
        return ResponseEntity.ok(worldText);
    }
}
