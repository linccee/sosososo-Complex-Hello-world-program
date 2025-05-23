package com.overengineered.client.command;

import com.overengineered.client.service.HelloWorldService;
import lombok.extern.slf4j.Slf4j;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

/**
 * The main command for the Hello World Client.
 * This command is responsible for handling user input and displaying output.
 */
@Component
@Command(name = "hello-world", 
        mixinStandardHelpOptions = true, 
        version = "Hello World Client 1.0",
        description = "An absurdly complex command-line client for generating Hello World messages")
@Slf4j
public class HelloWorldCommand implements Callable<Integer> {

    private final HelloWorldService helloWorldService;

    @Option(names = {"-l", "--language"}, description = "Language code (e.g., en, fr, es)")
    private String language = "en";

    @Option(names = {"-f", "--formality"}, description = "Formality level (1-5, where 1 is casual and 5 is very formal)")
    private Integer formalityLevel = 3;

    @Option(names = {"-p", "--planet"}, description = "Planet type (EARTH, MARS, VENUS, JUPITER, SATURN, NEPTUNE, URANUS, MERCURY, PLUTO)")
    private String planetType = "EARTH";

    @Option(names = {"-s", "--scope"}, description = "Geographical scope (GLOBAL, CONTINENTAL, REGIONAL, NATIONAL, LOCAL)")
    private String scope = "GLOBAL";

    @Option(names = {"-d", "--delimiter"}, description = "Delimiter between hello and world")
    private String delimiter = " ";

    @Option(names = {"-u", "--uppercase"}, description = "Convert output to uppercase")
    private boolean uppercase = false;

    @Option(names = {"-r", "--reversed"}, description = "Reverse the output")
    private boolean reversed = false;

    @Option(names = {"-e", "--encrypted"}, description = "Encrypt the output")
    private boolean encrypted = false;

    @Option(names = {"-a", "--async"}, description = "Generate message asynchronously")
    private boolean async = false;

    @Option(names = {"-i", "--interval"}, description = "Poll interval for async requests (in milliseconds)")
    private long pollInterval = 500;

    @Option(names = {"-c", "--color"}, description = "Use colored output")
    private boolean colorOutput = true;

    public HelloWorldCommand(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    @Override
    public Integer call() throws Exception {
        try {
            AnsiConsole.systemInstall();
            
            printBanner();
            
            log.info("Generating Hello World message with parameters: language={}, formality={}, planet={}, scope={}",
                    language, formalityLevel, planetType, scope);
            
            // Build the request
            HelloWorldRequest request = HelloWorldRequest.builder()
                    .language(language)
                    .formalityLevel(formalityLevel)
                    .planetType(planetType)
                    .scope(scope)
                    .delimiter(delimiter)
                    .uppercase(uppercase)
                    .reversed(reversed)
                    .encrypted(encrypted)
                    .build();
            
            // Generate the message
            HelloWorldResponse response;
            if (async) {
                System.out.println(colorize("Generating message asynchronously...", Ansi.Color.CYAN));
                response = helloWorldService.generateHelloWorldAsync(request, pollInterval);
            } else {
                System.out.println(colorize("Generating message...", Ansi.Color.CYAN));
                response = helloWorldService.generateHelloWorld(request);
            }
            
            // Display the result
            displayResponse(response);
            
            return 0;
        } catch (Exception e) {
            System.err.println(colorize("Error: " + e.getMessage(), Ansi.Color.RED));
            log.error("Error generating Hello World message", e);
            return 1;
        } finally {
            AnsiConsole.systemUninstall();
        }
    }
    
    /**
     * Display the HelloWorldResponse in a formatted way.
     *
     * @param response The response to display
     */
    private void displayResponse(HelloWorldResponse response) {
        System.out.println(colorize("┌───────────────────────────────────────┐", Ansi.Color.GREEN));
        System.out.println(colorize("│         HELLO WORLD RESPONSE          │", Ansi.Color.GREEN));
        System.out.println(colorize("├───────────────────────────────────────┤", Ansi.Color.GREEN));
        System.out.println(colorize("│ Message:    ", Ansi.Color.GREEN) + colorize(response.getMessage(), Ansi.Color.YELLOW));
        System.out.println(colorize("│ Hello Part: ", Ansi.Color.GREEN) + colorize(response.getHelloText(), Ansi.Color.YELLOW));
        System.out.println(colorize("│ World Part: ", Ansi.Color.GREEN) + colorize(response.getWorldText(), Ansi.Color.YELLOW));
        System.out.println(colorize("│ Language:   ", Ansi.Color.GREEN) + colorize(response.getLanguage(), Ansi.Color.WHITE));
        System.out.println(colorize("│ Formality:  ", Ansi.Color.GREEN) + colorize(response.getFormalityLevel().toString(), Ansi.Color.WHITE));
        System.out.println(colorize("│ Planet:     ", Ansi.Color.GREEN) + colorize(response.getPlanetType(), Ansi.Color.WHITE));
        System.out.println(colorize("│ Scope:      ", Ansi.Color.GREEN) + colorize(response.getScope(), Ansi.Color.WHITE));
        System.out.println(colorize("│ Generated:  ", Ansi.Color.GREEN) + colorize(response.getGeneratedAt(), Ansi.Color.WHITE));
        System.out.println(colorize("│ Time (ms):  ", Ansi.Color.GREEN) + colorize(response.getGenerationTimeMillis().toString(), Ansi.Color.WHITE));
        System.out.println(colorize("└───────────────────────────────────────┘", Ansi.Color.GREEN));
    }
    
    /**
     * Print a colorful banner.
     */
    private void printBanner() {
        System.out.println(colorize("╔═══════════════════════════════════════════════╗", Ansi.Color.BLUE));
        System.out.println(colorize("║                                               ║", Ansi.Color.BLUE));
        System.out.println(colorize("║   OVERENGINEERED HELLO WORLD CLIENT v1.0.0    ║", Ansi.Color.BLUE));
        System.out.println(colorize("║                                               ║", Ansi.Color.BLUE));
        System.out.println(colorize("╚═══════════════════════════════════════════════╝", Ansi.Color.BLUE));
        System.out.println();
    }
    
    /**
     * Colorize text if color output is enabled.
     *
     * @param text The text to colorize
     * @param color The color to use
     * @return The colorized text
     */
    private String colorize(String text, Ansi.Color color) {
        if (colorOutput) {
            return Ansi.ansi().fg(color).a(text).reset().toString();
        } else {
            return text;
        }
    }
    
    /**
     * Simple model for the request parameters.
     */
    @lombok.Data
    @lombok.Builder
    public static class HelloWorldRequest {
        private String language;
        private Integer formalityLevel;
        private String planetType;
        private String scope;
        private String delimiter;
        private Boolean uppercase;
        private Boolean reversed;
        private Boolean encrypted;
    }
    
    /**
     * Simple model for the response.
     */
    @lombok.Data
    @lombok.Builder
    public static class HelloWorldResponse {
        private String id;
        private String message;
        private String helloText;
        private String worldText;
        private String language;
        private Integer formalityLevel;
        private String planetType;
        private String scope;
        private String delimiter;
        private String generatedAt;
        private Long generationTimeMillis;
        private String requestId;
        private String helloStrategy;
        private String worldStrategy;
    }
}
