package com.overengineered.hello.mapper;

import com.overengineered.hello.dto.HelloGreetingDto;
import com.overengineered.hello.entity.HelloGreeting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * MapStruct mapper interface for converting between HelloGreeting entity and DTO.
 * Even though this is a simple conversion, we've added lots of configuration options.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface HelloGreetingMapper {

    /**
     * Convert entity to DTO.
     * 
     * @param entity The HelloGreeting entity
     * @return The HelloGreetingDto
     */
    HelloGreetingDto toDto(HelloGreeting entity);

    /**
     * Convert DTO to entity.
     * 
     * @param dto The HelloGreetingDto
     * @return The HelloGreeting entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    HelloGreeting toEntity(HelloGreetingDto dto);

    /**
     * Update entity from DTO.
     * 
     * @param dto The HelloGreetingDto with updates
     * @param entity The existing HelloGreeting entity
     * @return The updated HelloGreeting entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    HelloGreeting updateEntityFromDto(HelloGreetingDto dto, @MappingTarget HelloGreeting entity);

    /**
     * Convert a list of entities to a list of DTOs.
     * 
     * @param entities The list of HelloGreeting entities
     * @return The list of HelloGreetingDtos
     */
    List<HelloGreetingDto> toDtoList(List<HelloGreeting> entities);
}
