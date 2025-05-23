package com.overengineered.hello.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * A simple implementation of the HelloGenerationStrategy that returns the standard "Hello" text.
 * Despite being simple, we've added logging and extra logic to make it complex.
 */
@Component
@Slf4j
public class StandardHelloStrategy implements HelloGenerationStrategy {

    @Override
    public String generateHello(String input, String language) {
        log.info("Generating standard hello greeting in language: {}", language);
        
        String result;
        // Pointlessly complex implementation for different languages
        switch (language.toLowerCase()) {
            case "en":
                result = "Hello";
                break;
            case "es":
                result = "Hola";
                break;
            case "fr":
                result = "Bonjour";
                break;
            case "de":
                result = "Hallo";
                break;
            case "it":
                result = "Ciao";
                break;
            case "zh":
                result = "你好";
                break;
            case "ja":
                result = "こんにちは";
                break;
            default:
                log.warn("Unsupported language: {}. Defaulting to English.", language);
                result = "Hello";
        }
        
        log.debug("Generated standard greeting: {}", result);
        return result;
    }

    @Override
    public String getStrategyName() {
        return "STANDARD";
    }

    @Override
    public boolean isApplicable(String language, int formalityLevel) {
        // This is the default strategy, so it's always applicable
        return true;
    }
}
