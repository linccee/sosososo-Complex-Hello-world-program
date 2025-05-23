package com.overengineered.aggregator.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client for communicating with the Hello service.
 * This interface allows the Aggregator service to make REST calls to the Hello service.
 */
@FeignClient(name = "hello-service", fallback = HelloServiceClientFallback.class)
public interface HelloServiceClient {

    /**
     * Call the Hello service to generate a hello greeting.
     *
     * @param language The language code
     * @param formalityLevel The formality level (1-5)
     * @return The generated hello greeting
     */
    @GetMapping("/api/v1/greetings/generate")
    String generateHello(@RequestParam("language") String language, 
                         @RequestParam("formalityLevel") int formalityLevel);
}
