package com.overengineered.world.strategy;

import com.overengineered.world.document.WorldEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * A simple implementation of the WorldGenerationStrategy that returns the standard "World" text.
 * Despite being simple, we've added logging and extra logic to make it complex.
 */
@Component
@Slf4j
public class StandardWorldStrategy implements WorldGenerationStrategy {

    @Override
    public String generateWorld(String input, String language, WorldEntity.PlanetType planetType) {
        log.info("Generating standard world text in language: {} for planet type: {}", language, planetType);
        
        String result;
        // Pointlessly complex implementation for different languages
        if (planetType == WorldEntity.PlanetType.EARTH) {
            switch (language.toLowerCase()) {
                case "en":
                    result = "World";
                    break;
                case "es":
                    result = "Mundo";
                    break;
                case "fr":
                    result = "Monde";
                    break;
                case "de":
                    result = "Welt";
                    break;
                case "it":
                    result = "Mondo";
                    break;
                case "zh":
                    result = "世界";
                    break;
                case "ja":
                    result = "世界";
                    break;
                default:
                    log.warn("Unsupported language: {}. Defaulting to English.", language);
                    result = "World";
            }
        } else {
            // Different planet types get different results
            switch (planetType) {
                case MARS:
                    result = "Mars";
                    break;
                case JUPITER:
                    result = "Jupiter";
                    break;
                case SATURN:
                    result = "Saturn";
                    break;
                case VENUS:
                    result = "Venus";
                    break;
                case MERCURY:
                    result = "Mercury";
                    break;
                case NEPTUNE:
                    result = "Neptune";
                    break;
                case URANUS:
                    result = "Uranus";
                    break;
                case PLUTO:
                    result = "Pluto (not a planet anymore, but we include it anyway)";
                    break;
                default:
                    result = "Universe";
            }
        }
        
        log.debug("Generated standard world text: {}", result);
        return result;
    }

    @Override
    public String getStrategyName() {
        return "STANDARD";
    }

    @Override
    public boolean isApplicable(String language, WorldEntity.PlanetType planetType, WorldEntity.GeographicalScope scope) {
        // This is the default strategy, so it's always applicable
        return true;
    }
}
