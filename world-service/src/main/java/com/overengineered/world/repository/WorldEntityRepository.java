package com.overengineered.world.repository;

import com.overengineered.world.document.WorldEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for accessing the WorldEntity documents.
 * Even though we only need to retrieve a simple string, we've added complex queries.
 */
@Repository
public interface WorldEntityRepository extends MongoRepository<WorldEntity, String> {

    /**
     * Find a world entity by its UUID.
     *
     * @param uuid The UUID of the world entity
     * @return Optional containing the found entity or empty
     */
    Optional<WorldEntity> findByUuid(String uuid);

    /**
     * Find all active world entities in a specific language.
     *
     * @param language The language code
     * @return List of active world entities
     */
    List<WorldEntity> findByLanguageAndIsActiveOrderByPriorityDesc(String language, Boolean isActive);

    /**
     * Find the world entity with the highest priority for a specific language and planet type.
     *
     * @param language The language code
     * @param planetType The planet type
     * @return Optional containing the highest priority world entity
     */
    @Query("{'language': ?0, 'planetType': ?1, 'isActive': true}")
    List<WorldEntity> findByLanguageAndPlanetTypeAndActiveOrderByPriorityDesc(String language, 
                                                                           WorldEntity.PlanetType planetType);

    /**
     * Find the world entity with the highest priority for a specific language and geographical scope.
     *
     * @param language The language code
     * @param scope The geographical scope
     * @return Optional containing the highest priority world entity
     */
    Optional<WorldEntity> findFirstByLanguageAndGeographicalScopeAndIsActiveTrueOrderByPriorityDesc(
            String language, WorldEntity.GeographicalScope scope);

    /**
     * Count the number of world entities for a specific language.
     *
     * @param language The language code
     * @return The count of world entities
     */
    long countByLanguage(String language);
}
