package com.overengineered.hello.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * A strategy that reverses the hello greeting text.
 * This is a completely unnecessary implementation that demonstrates the Strategy pattern.
 */
@Component
@Slf4j
public class ReversedHelloStrategy implements HelloGenerationStrategy {

    @Override
    public String generateHello(String input, String language) {
        log.info("Generating reversed hello greeting in language: {}", language);
        
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
        
        // Unnecessarily complex way to reverse a string
        StringBuilder reversed = new StringBuilder();
        for (int i = standardGreeting.length() - 1; i >= 0; i--) {
            reversed.append(standardGreeting.charAt(i));
        }
        
        String result = reversed.toString();
        log.debug("Generated reversed greeting: {}", result);
        return result;
    }

    @Override
    public String getStrategyName() {
        return "REVERSED";
    }

    @Override
    public boolean isApplicable(String language, int formalityLevel) {
        // Only apply this strategy for specific languages and formality levels
        return formalityLevel < 3 && 
               (language.equalsIgnoreCase("en") || 
                language.equalsIgnoreCase("es") || 
                language.equalsIgnoreCase("fr"));
    }
}
