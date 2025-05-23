package com.overengineered.hello.service;

import com.overengineered.hello.dto.HelloGreetingDto;
import com.overengineered.hello.entity.HelloGreeting;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing hello greetings.
 * Defines methods for retrieving, creating, updating and generating hello greetings.
 */
public interface HelloService {

    /**
     * Get all hello greetings.
     *
     * @return List of all hello greeting DTOs
     */
    List<HelloGreetingDto> getAllGreetings();
    
    /**
     * Get a hello greeting by ID.
     *
     * @param id The ID of the greeting to retrieve
     * @return Optional containing the greeting if found
     */
    Optional<HelloGreetingDto> getGreetingById(Long id);
    
    /**
     * Get a hello greeting by UUID.
     *
     * @param uuid The UUID of the greeting to retrieve
     * @return Optional containing the greeting if found
     */
    Optional<HelloGreetingDto> getGreetingByUuid(String uuid);
    
    /**
     * Save a new hello greeting.
     *
     * @param greetingDto The greeting DTO to save
     * @return The saved greeting DTO with generated ID
     */
    HelloGreetingDto saveGreeting(HelloGreetingDto greetingDto);
    
    /**
     * Update an existing hello greeting.
     *
     * @param id The ID of the greeting to update
     * @param greetingDto The updated greeting DTO
     * @return The updated greeting DTO
     */
    HelloGreetingDto updateGreeting(Long id, HelloGreetingDto greetingDto);
    
    /**
     * Delete a hello greeting by ID.
     *
     * @param id The ID of the greeting to delete
     */
    void deleteGreeting(Long id);
    
    /**
     * Generate a hello greeting based on language and formality.
     *
     * @param language The language code
     * @param formalityLevel The formality level (1-5)
     * @return The generated hello greeting
     */
    String generateHello(String language, int formalityLevel);
}
