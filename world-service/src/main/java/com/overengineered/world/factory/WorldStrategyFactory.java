package com.overengineered.world.factory;

import com.overengineered.world.document.WorldEntity;
import com.overengineered.world.strategy.WorldGenerationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Factory class for selecting the appropriate WorldGenerationStrategy.
 * This demonstrates the Factory Pattern, which is completely unnecessary for this simple task.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class WorldStrategyFactory {

    private final List<WorldGenerationStrategy> strategies;

    /**
     * Select the appropriate strategy based on context parameters.
     *
     * @param language The language code
     * @param planetType The planet type
     * @param scope The geographical scope
     * @return The selected strategy
     */
    public WorldGenerationStrategy getStrategy(String language, WorldEntity.PlanetType planetType, WorldEntity.GeographicalScope scope) {
        log.info("Selecting world strategy for language: {}, planet type: {}, scope: {}", language, planetType, scope);
        
        // Find all applicable strategies
        List<WorldGenerationStrategy> applicableStrategies = strategies.stream()
                .filter(strategy -> strategy.isApplicable(language, planetType, scope))
                .toList();
        
        if (applicableStrategies.isEmpty()) {
            log.warn("No applicable strategies found. Using default strategy.");
            // Return the first strategy as default (should be StandardWorldStrategy)
            return strategies.get(0);
        }
        
        // If multiple strategies are applicable, select based on some criteria
        if (applicableStrategies.size() > 1) {
            log.debug("Multiple applicable strategies found: {}", 
                    applicableStrategies.stream().map(WorldGenerationStrategy::getStrategyName).toList());
            
            // For demonstration, prioritize strategies in a specific order
            for (String strategyName : List.of("EMPHASIZED", "STANDARD")) {
                for (WorldGenerationStrategy strategy : applicableStrategies) {
                    if (strategy.getStrategyName().equals(strategyName)) {
                        log.debug("Selected strategy: {}", strategyName);
                        return strategy;
                    }
                }
            }
        }
        
        // Return the first applicable strategy
        WorldGenerationStrategy selected = applicableStrategies.get(0);
        log.debug("Selected strategy: {}", selected.getStrategyName());
        return selected;
    }
}
