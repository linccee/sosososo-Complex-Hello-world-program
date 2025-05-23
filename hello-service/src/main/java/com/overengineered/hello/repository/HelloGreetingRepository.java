package com.overengineered.hello.repository;

import com.overengineered.hello.entity.HelloGreeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

/**
 * Repository for accessing the HelloGreeting entities.
 * Even though we only need to retrieve a simple string, we've added complex queries and locking mechanisms.
 */
@Repository
public interface HelloGreetingRepository extends JpaRepository<HelloGreeting, Long> {

    /**
     * Find a greeting by its UUID with pessimistic locking.
     *
     * @param uuid The UUID of the greeting
     * @return Optional containing the found greeting or empty
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<HelloGreeting> findByUuid(String uuid);

    /**
     * Find all active greetings in a specific language.
     *
     * @param language The language code
     * @return List of active greetings
     */
    List<HelloGreeting> findByLanguageAndIsActiveOrderByPriorityDesc(String language, Boolean isActive);

    /**
     * Find the greeting with the highest priority for a specific language and formality.
     *
     * @param language The language code
     * @param formality The formality level
     * @return Optional containing the highest priority greeting
     */
    @Query("SELECT h FROM HelloGreeting h WHERE h.language = :language AND h.formality = :formality AND h.isActive = true ORDER BY h.priority DESC")
    Optional<HelloGreeting> findTopPriorityByLanguageAndFormality(@Param("language") String language, 
                                                                @Param("formality") HelloGreeting.Formality formality);

    /**
     * Count the number of greetings for a specific language.
     *
     * @param language The language code
     * @return The count of greetings
     */
    long countByLanguage(String language);
}
