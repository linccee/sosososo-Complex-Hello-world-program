package com.overengineered.client.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for service discovery.
 */
@Configuration
@EnableDiscoveryClient
public class DiscoveryConfig {
    // This configuration enables the client to discover services through Eureka
}
