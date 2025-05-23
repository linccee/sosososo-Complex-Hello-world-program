package com.overengineered.aggregator.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Swagger/OpenAPI documentation.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configure OpenAPI documentation for the service.
     *
     * @return OpenAPI configuration
     */
    @Bean
    public OpenAPI aggregatorServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hello World Aggregator Service API")
                        .description("This absurdly over-engineered API combines the 'Hello' and 'World' parts to create a complete 'Hello World' message")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Overengineering Team")
                                .email("overengineering@example.com")
                                .url("https://github.com/overengineered/hello-world"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
