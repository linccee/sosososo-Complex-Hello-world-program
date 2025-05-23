package com.overengineered.world.mapper;

import com.overengineered.world.document.WorldEntity;
import com.overengineered.world.dto.WorldEntityDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * MapStruct mapper interface for converting between WorldEntity document and DTO.
 * Even though this is a simple conversion, we've added lots of configuration options.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WorldEntityMapper {

    /**
     * Convert document to DTO.
     * 
     * @param entity The WorldEntity document
     * @return The WorldEntityDto
     */
    WorldEntityDto toDto(WorldEntity entity);

    /**
     * Convert DTO to document.
     * 
     * @param dto The WorldEntityDto
     * @return The WorldEntity document
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    WorldEntity toEntity(WorldEntityDto dto);

    /**
     * Update document from DTO.
     * 
     * @param dto The WorldEntityDto with updates
     * @param entity The existing WorldEntity document
     * @return The updated WorldEntity document
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    WorldEntity updateEntityFromDto(WorldEntityDto dto, @MappingTarget WorldEntity entity);

    /**
     * Convert a list of documents to a list of DTOs.
     * 
     * @param entities The list of WorldEntity documents
     * @return The list of WorldEntityDtos
     */
    List<WorldEntityDto> toDtoList(List<WorldEntity> entities);
}
