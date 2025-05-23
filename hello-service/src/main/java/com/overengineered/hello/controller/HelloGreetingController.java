package com.overengineered.hello.controller;

import com.overengineered.hello.dto.HelloGreetingDto;
import com.overengineered.hello.service.HelloService;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * REST controller for managing hello greetings.
 * This class is absurdly over-engineered for a simple hello world application.
 */
@RestController
@RequestMapping("/api/v1/greetings")
@Validated
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Hello Greeting API", description = "Operations for managing hello greetings")
public class HelloGreetingController {

    private final HelloService helloService;

    /**
     * GET /api/v1/greetings : Get all greetings
     *
     * @return ResponseEntity with the list of greetings
     */
    @GetMapping
    @Operation(summary = "Get all hello greetings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved greetings"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<HelloGreetingDto>> getAllGreetings() {
        log.info("REST request to get all hello greetings");
        List<HelloGreetingDto> greetings = helloService.getAllGreetings();
        return ResponseEntity.ok(greetings);
    }

    /**
     * GET /api/v1/greetings/{id} : Get a greeting by ID
     *
     * @param id The ID of the greeting to retrieve
     * @return ResponseEntity with the greeting
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get hello greeting by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved greeting"),
            @ApiResponse(responseCode = "404", description = "Greeting not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<HelloGreetingDto> getGreetingById(
            @Parameter(description = "ID of the greeting", required = true)
            @PathVariable Long id) {
        
        log.info("REST request to get hello greeting with ID: {}", id);
        return helloService.getGreetingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/v1/greetings : Create a new greeting
     *
     * @param greetingDto The greeting to create
     * @return ResponseEntity with the created greeting
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new hello greeting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created greeting"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<HelloGreetingDto> createGreeting(
            @Parameter(description = "Greeting to create", required = true, schema = @Schema(implementation = HelloGreetingDto.class))
            @Valid @RequestBody HelloGreetingDto greetingDto) {
        
        log.info("REST request to create hello greeting: {}", greetingDto);
        HelloGreetingDto savedGreeting = helloService.saveGreeting(greetingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGreeting);
    }

    /**
     * PUT /api/v1/greetings/{id} : Update an existing greeting
     *
     * @param id The ID of the greeting to update
     * @param greetingDto The updated greeting
     * @return ResponseEntity with the updated greeting
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update an existing hello greeting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated greeting"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Greeting not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<HelloGreetingDto> updateGreeting(
            @Parameter(description = "ID of the greeting to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated greeting", required = true, schema = @Schema(implementation = HelloGreetingDto.class))
            @Valid @RequestBody HelloGreetingDto greetingDto) {
        
        log.info("REST request to update hello greeting with ID: {}", id);
        HelloGreetingDto updatedGreeting = helloService.updateGreeting(id, greetingDto);
        return ResponseEntity.ok(updatedGreeting);
    }

    /**
     * DELETE /api/v1/greetings/{id} : Delete a greeting
     *
     * @param id The ID of the greeting to delete
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a hello greeting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted greeting"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Greeting not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteGreeting(
            @Parameter(description = "ID of the greeting to delete", required = true)
            @PathVariable Long id) {
        
        log.info("REST request to delete hello greeting with ID: {}", id);
        helloService.deleteGreeting(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/v1/greetings/generate : Generate a hello greeting
     *
     * @param language The language code
     * @param formalityLevel The formality level (1-5)
     * @return ResponseEntity with the generated greeting
     */
    @GetMapping("/generate")
    @Operation(summary = "Generate a hello greeting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully generated greeting"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> generateHello(
            @Parameter(description = "Language code (e.g., en, fr, es)", required = true)
            @RequestParam @NotBlank String language,
            
            @Parameter(description = "Formality level (1-5, where 1 is casual and 5 is very formal)", required = true)
            @RequestParam @Min(1) @Max(5) int formalityLevel) {
        
        log.info("REST request to generate hello in language: {} with formality level: {}", language, formalityLevel);
        String greeting = helloService.generateHello(language, formalityLevel);
        return ResponseEntity.ok(greeting);
    }
}
