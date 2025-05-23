package com.overengineered.world.strategy;

import com.overengineered.world.document.WorldEntity;

/**
 * Strategy interface for different ways to generate or transform a world text.
 * This interface demonstrates the Strategy Pattern, allowing for different implementations
 * of generating a world text.
 */
public interface WorldGenerationStrategy {

    /**
     * Generate or transform a world text based on the specific strategy.
     *
     * @param input The input text to transform (optional, can be null)
     * @param language The language code to use
     * @param planetType The planet type to use
     * @return The generated or transformed world text
     */
    String generateWorld(String input, String language, WorldEntity.PlanetType planetType);
    
    /**
     * Get the name of this strategy.
     *
     * @return The strategy name
     */
    String getStrategyName();
    
    /**
     * Check if this strategy is applicable for the given context.
     *
     * @param language The language code
     * @param planetType The planet type
     * @param scope The geographical scope
     * @return true if this strategy should be applied, false otherwise
     */
    boolean isApplicable(String language, WorldEntity.PlanetType planetType, WorldEntity.GeographicalScope scope);
}
