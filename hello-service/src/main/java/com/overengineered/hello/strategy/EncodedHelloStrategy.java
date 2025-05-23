package com.overengineered.hello.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.nio.charset.StandardCharsets;

/**
 * A strategy that encodes the hello greeting text in Base64.
 * Another completely unnecessary implementation that demonstrates the Strategy pattern.
 */
@Component
@Slf4j
public class EncodedHelloStrategy implements HelloGenerationStrategy {

    @Override
    public String generateHello(String input, String language) {
        log.info("Generating encoded hello greeting in language: {}", language);
        
        // First get the standard greeting for the language
        String standardGreeting;
        switch (language.toLowerCase()) {
            case "en":
                standardGreeting = "Hello";
                break;
            case "es":
                standardGreeting = "Hola";
                break;
            case "fr":
                standardGreeting = "Bonjour";
                break;
            case "de":
                standardGreeting = "Hallo";
                break;
            case "it":
                standardGreeting = "Ciao";
                break;
            case "zh":
                standardGreeting = "你好";
                break;
            case "ja":
                standardGreeting = "こんにちは";
                break;
            default:
                log.warn("Unsupported language: {}. Defaulting to English.", language);
                standardGreeting = "Hello";
        }
        
        // Encode the greeting in Base64
        String encoded = Base64.getEncoder().encodeToString(standardGreeting.getBytes(StandardCharsets.UTF_8));
        
        log.debug("Generated encoded greeting: {}", encoded);
        return encoded;
    }

    @Override
    public String getStrategyName() {
        return "ENCODED";
    }

    @Override
    public boolean isApplicable(String language, int formalityLevel) {
        // Only apply this strategy for high formality levels
        return formalityLevel >= 4;
    }
}
