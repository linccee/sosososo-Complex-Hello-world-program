package com.overengineered.world.service;

import com.overengineered.world.document.WorldEntity;
import com.overengineered.world.dto.WorldEntityDto;
import com.overengineered.world.event.WorldGeneratedEvent;
import com.overengineered.world.exception.WorldEntityNotFoundException;
import com.overengineered.world.factory.WorldStrategyFactory;
import com.overengineered.world.mapper.WorldEntityMapper;
import com.overengineered.world.repository.WorldEntityRepository;
import com.overengineered.world.strategy.WorldGenerationStrategy;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the WorldService interface.
 * This class demonstrates extreme over-engineering for a simple hello world program.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class WorldServiceImpl implements WorldService {

    private final WorldEntityRepository worldEntityRepository;
    private final WorldEntityMapper worldEntityMapper;
    private final WorldStrategyFactory worldStrategyFactory;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Cacheable(value = "worldEntities")
    public List<WorldEntityDto> getAllWorldEntities() {
        log.info("Retrieving all world entities");
        List<WorldEntity> entities = worldEntityRepository.findAll(Sort.by(Sort.Direction.DESC, "priority"));
        return worldEntityMapper.toDtoList(entities);
    }

    @Override
    @Cacheable(value = "worldEntities", key = "#id")
    public Optional<WorldEntityDto> getWorldEntityById(String id) {
        log.info("Retrieving world entity with ID: {}", id);
        return worldEntityRepository.findById(id)
                .map(worldEntityMapper::toDto);
    }

    @Override
    @Cacheable(value = "worldEntities", key = "#uuid")
    public Optional<WorldEntityDto> getWorldEntityByUuid(String uuid) {
        log.info("Retrieving world entity with UUID: {}", uuid);
        return worldEntityRepository.findByUuid(uuid)
                .map(worldEntityMapper::toDto);
    }

    @Override
    @CachePut(value = "worldEntities", key = "#result.id")
    public WorldEntityDto saveWorldEntity(@Valid WorldEntityDto entityDto) {
        log.info("Saving new world entity: {}", entityDto);
        WorldEntity entity = worldEntityMapper.toEntity(entityDto);
        WorldEntity savedEntity = worldEntityRepository.save(entity);
        return worldEntityMapper.toDto(savedEntity);
    }

    @Override
    @CachePut(value = "worldEntities", key = "#id")
    public WorldEntityDto updateWorldEntity(String id, @Valid WorldEntityDto entityDto) {
        log.info("Updating world entity with ID: {}", id);
        WorldEntity entity = worldEntityRepository.findById(id)
                .orElseThrow(() -> new WorldEntityNotFoundException("World entity not found with ID: " + id));
        
        WorldEntity updatedEntity = worldEntityMapper.updateEntityFromDto(entityDto, entity);
        WorldEntity savedEntity = worldEntityRepository.save(updatedEntity);
        return worldEntityMapper.toDto(savedEntity);
    }

    @Override
    @CacheEvict(value = "worldEntities", key = "#id")
    public void deleteWorldEntity(String id) {
        log.info("Deleting world entity with ID: {}", id);
        if (worldEntityRepository.existsById(id)) {
            worldEntityRepository.deleteById(id);
        } else {
            throw new WorldEntityNotFoundException("World entity not found with ID: " + id);
        }
    }

    @Override
    @CircuitBreaker(name = "worldService", fallbackMethod = "fallbackGenerateWorld")
    @Retry(name = "worldService")
    public String generateWorld(String language, WorldEntity.PlanetType planetType, WorldEntity.GeographicalScope scope) {
        log.info("Generating world text in language: {} with planet type: {} and scope: {}", 
                language, planetType, scope);
        
        // Select appropriate strategy using factory
        WorldGenerationStrategy strategy = worldStrategyFactory.getStrategy(language, planetType, scope);
        
        // Generate the world text
        String worldText = strategy.generateWorld(null, language, planetType);
        
        // Publish event
        eventPublisher.publishEvent(new WorldGeneratedEvent(this, worldText, language, planetType, scope, strategy.getStrategyName()));
        
        return worldText;
    }
    
    /**
     * Fallback method for generateWorld in case of failures.
     *
     * @param language The language code
     * @param planetType The planet type
     * @param scope The geographical scope
     * @param e The exception that triggered the fallback
     * @return A fallback world text
     */
    private String fallbackGenerateWorld(String language, WorldEntity.PlanetType planetType, 
                                        WorldEntity.GeographicalScope scope, Exception e) {
        log.warn("Fallback method invoked for generateWorld due to: {}", e.getMessage());
        return "World (fallback)";
    }
}
