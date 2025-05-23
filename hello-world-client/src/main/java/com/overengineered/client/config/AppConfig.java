package com.overengineered.client.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * General application configuration.
 */
@Configuration
@EnableConfigurationProperties(ClientProperties.class)
public class AppConfig {

    /**
     * Creates a WebClient bean for making HTTP requests.
     *
     * @return WebClient instance
     */
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .build();
    }
}
