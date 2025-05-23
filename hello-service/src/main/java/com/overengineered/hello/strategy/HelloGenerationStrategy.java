package com.overengineered.hello.strategy;

/**
 * Strategy interface for different ways to generate or transform a hello greeting.
 * This interface demonstrates the Strategy Pattern, allowing for different implementations
 * of generating a hello greeting.
 */
public interface HelloGenerationStrategy {

    /**
     * Generate or transform a hello greeting based on the specific strategy.
     *
     * @param input The input greeting text to transform (optional, can be null)
     * @param language The language code to use
     * @return The generated or transformed greeting
     */
    String generateHello(String input, String language);
    
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
     * @param formalityLevel The level of formality (1-5)
     * @return true if this strategy should be applied, false otherwise
     */
    boolean isApplicable(String language, int formalityLevel);
}
