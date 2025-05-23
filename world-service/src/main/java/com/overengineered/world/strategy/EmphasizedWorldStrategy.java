package com.overengineered.world.strategy;

import com.overengineered.world.document.WorldEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * A strategy that capitalizes and adds emphasis to the world text.
 * This is a completely unnecessary implementation that demonstrates the Strategy pattern.
 */
@Component
@Slf4j
public class EmphasizedWorldStrategy implements WorldGenerationStrategy {

    @Override
    public String generateWorld(String input, String language, WorldEntity.PlanetType planetType) {
        log.info("Generating emphasized world text in language: {} for planet type: {}", language, planetType);
        
        // First get the standard text for the language and planet type
        String standardText;
        if (planetType == WorldEntity.PlanetType.EARTH) {
            switch (language.toLowerCase()) {
                case "en":
                    standardText = "World";
                    break;
                case "es":
                    standardText = "Mundo";
                    break;
                case "fr":
                    standardText = "Monde";
                    break;
                case "de":
                    standardText = "Welt";
                    break;
                case "it":
                    standardText = "Mondo";
                    break;
                case "zh":
                    standardText = "世界";
                    break;
                case "ja":
                    standardText = "世界";
                    break;
                default:
                    log.warn("Unsupported language: {}. Defaulting to English.", language);
                    standardText = "World";
            }
        } else {
            // Different planet types get different results
            switch (planetType) {
                case MARS:
                    standardText = "Mars";
                    break;
                case JUPITER:
                    standardText = "Jupiter";
                    break;
                case SATURN:
                    standardText = "Saturn";
                    break;
                case VENUS:
                    standardText = "Venus";
                    break;
                case MERCURY:
                    standardText = "Mercury";
                    break;
                case NEPTUNE:
                    standardText = "Neptune";
                    break;
                case URANUS:
                    standardText = "Uranus";
                    break;
                case PLUTO:
                    standardText = "Pluto";
                    break;
                default:
                    standardText = "Universe";
            }
        }
        
        // Now add emphasis (uppercase and exclamation points)
        String result = standardText.toUpperCase() + "!!!";
        
        log.debug("Generated emphasized world text: {}", result);
        return result;
    }

    @Override
    public String getStrategyName() {
        return "EMPHASIZED";
    }

    @Override
    public boolean isApplicable(String language, WorldEntity.PlanetType planetType, WorldEntity.GeographicalScope scope) {
        // Only apply this strategy for specific conditions
        return scope == WorldEntity.GeographicalScope.GLOBAL && 
               (planetType == WorldEntity.PlanetType.EARTH || 
                planetType == WorldEntity.PlanetType.MARS);
    }
}
