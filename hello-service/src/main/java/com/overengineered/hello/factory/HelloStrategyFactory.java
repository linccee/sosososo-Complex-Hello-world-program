package com.overengineered.hello.factory;

import com.overengineered.hello.strategy.HelloGenerationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Factory class for selecting the appropriate HelloGenerationStrategy.
 * This demonstrates the Factory Pattern, which is completely unnecessary for this simple task.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class HelloStrategyFactory {

    private final List<HelloGenerationStrategy> strategies;

    /**
     * Select the appropriate strategy based on context parameters.
     *
     * @param language The language code
     * @param formalityLevel The formality level (1-5)
     * @return The selected strategy
     */
    public HelloGenerationStrategy getStrategy(String language, int formalityLevel) {
        log.info("Selecting hello strategy for language: {} and formality level: {}", language, formalityLevel);
        
        // Find all applicable strategies
        List<HelloGenerationStrategy> applicableStrategies = strategies.stream()
                .filter(strategy -> strategy.isApplicable(language, formalityLevel))
                .toList();
        
        if (applicableStrategies.isEmpty()) {
            log.warn("No applicable strategies found. Using default strategy.");
            // Return the first strategy as default (should be StandardHelloStrategy)
            return strategies.get(0);
        }
        
        // If multiple strategies are applicable, select based on some criteria
        if (applicableStrategies.size() > 1) {
            log.debug("Multiple applicable strategies found: {}", 
                    applicableStrategies.stream().map(HelloGenerationStrategy::getStrategyName).toList());
            
            // For demonstration, prioritize strategies in a specific order
            for (String strategyName : List.of("ENCODED", "REVERSED", "STANDARD")) {
                for (HelloGenerationStrategy strategy : applicableStrategies) {
                    if (strategy.getStrategyName().equals(strategyName)) {
                        log.debug("Selected strategy: {}", strategyName);
                        return strategy;
                    }
                }
            }
        }
        
        // Return the first applicable strategy
        HelloGenerationStrategy selected = applicableStrategies.get(0);
        log.debug("Selected strategy: {}", selected.getStrategyName());
        return selected;
    }
}
