package com.overengineered.aggregator.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.overengineered.aggregator.model.GeographicalScope;
import com.overengineered.aggregator.model.PlanetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Data Transfer Object for requesting a Hello World message.
 * Contains all the parameters needed to generate a customized message.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HelloWorldRequestDto {

    @NotBlank(message = "Language is required")
    private String language;
    
    @NotNull(message = "Formality level is required")
    @Min(value = 1, message = "Formality level must be between 1 and 5")
    @Max(value = 5, message = "Formality level must be between 1 and 5")
    private Integer formalityLevel;
    
    @NotNull(message = "Planet type is required")
    private PlanetType planetType;
    
    @NotNull(message = "Geographical scope is required")
    private GeographicalScope scope;
    
    private String delimiter;
    private Boolean uppercase;
    private Boolean reversed;
    private Boolean encrypted;
    
    /**
     * Static factory method to create a default request.
     *
     * @return A new HelloWorldRequestDto with default values
     */
    public static HelloWorldRequestDto createDefault() {
        return HelloWorldRequestDto.builder()
                .language("en")
                .formalityLevel(3)
                .planetType(PlanetType.EARTH)
                .scope(GeographicalScope.GLOBAL)
                .delimiter(" ")
                .uppercase(false)
                .reversed(false)
                .encrypted(false)
                .build();
    }
}
