package com.overengineered.aggregator.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client for communicating with the World service.
 * This interface allows the Aggregator service to make REST calls to the World service.
 */
@FeignClient(name = "world-service", fallback = WorldServiceClientFallback.class)
public interface WorldServiceClient {

    /**
     * Call the World service to generate a world text.
     *
     * @param language The language code
     * @param planetType The planet type
     * @param scope The geographical scope
     * @return The generated world text
     */
    @GetMapping("/api/v1/worlds/generate")
    String generateWorld(@RequestParam("language") String language, 
                         @RequestParam("planetType") String planetType,
                         @RequestParam("scope") String scope);
}
