package com.overengineered.hello.service;

import com.overengineered.hello.dto.HelloGreetingDto;
import com.overengineered.hello.entity.HelloGreeting;
import com.overengineered.hello.event.HelloGeneratedEvent;
import com.overengineered.hello.exception.GreetingNotFoundException;
import com.overengineered.hello.factory.HelloStrategyFactory;
import com.overengineered.hello.mapper.HelloGreetingMapper;
import com.overengineered.hello.repository.HelloGreetingRepository;
import com.overengineered.hello.strategy.HelloGenerationStrategy;
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
 * Implementation of the HelloService interface.
 * This class demonstrates extreme over-engineering for a simple hello world program.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class HelloServiceImpl implements HelloService {

    private final HelloGreetingRepository helloGreetingRepository;
    private final HelloGreetingMapper helloGreetingMapper;
    private final HelloStrategyFactory helloStrategyFactory;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "helloGreetings")
    public List<HelloGreetingDto> getAllGreetings() {
        log.info("Retrieving all hello greetings");
        List<HelloGreeting> greetings = helloGreetingRepository.findAll(Sort.by(Sort.Direction.DESC, "priority"));
        return helloGreetingMapper.toDtoList(greetings);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "helloGreetings", key = "#id")
    public Optional<HelloGreetingDto> getGreetingById(Long id) {
        log.info("Retrieving hello greeting with ID: {}", id);
        return helloGreetingRepository.findById(id)
                .map(helloGreetingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "helloGreetings", key = "#uuid")
    public Optional<HelloGreetingDto> getGreetingByUuid(String uuid) {
        log.info("Retrieving hello greeting with UUID: {}", uuid);
        return helloGreetingRepository.findByUuid(uuid)
                .map(helloGreetingMapper::toDto);
    }

    @Override
    @Transactional
    @CachePut(value = "helloGreetings", key = "#result.id")
    public HelloGreetingDto saveGreeting(@Valid HelloGreetingDto greetingDto) {
        log.info("Saving new hello greeting: {}", greetingDto);
        HelloGreeting entity = helloGreetingMapper.toEntity(greetingDto);
        HelloGreeting savedEntity = helloGreetingRepository.save(entity);
        return helloGreetingMapper.toDto(savedEntity);
    }

    @Override
    @Transactional
    @CachePut(value = "helloGreetings", key = "#id")
    public HelloGreetingDto updateGreeting(Long id, @Valid HelloGreetingDto greetingDto) {
        log.info("Updating hello greeting with ID: {}", id);
        HelloGreeting entity = helloGreetingRepository.findById(id)
                .orElseThrow(() -> new GreetingNotFoundException("Greeting not found with ID: " + id));
        
        HelloGreeting updatedEntity = helloGreetingMapper.updateEntityFromDto(greetingDto, entity);
        HelloGreeting savedEntity = helloGreetingRepository.save(updatedEntity);
        return helloGreetingMapper.toDto(savedEntity);
    }

    @Override
    @Transactional
    @CacheEvict(value = "helloGreetings", key = "#id")
    public void deleteGreeting(Long id) {
        log.info("Deleting hello greeting with ID: {}", id);
        if (helloGreetingRepository.existsById(id)) {
            helloGreetingRepository.deleteById(id);
        } else {
            throw new GreetingNotFoundException("Greeting not found with ID: " + id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @CircuitBreaker(name = "helloService", fallbackMethod = "fallbackGenerateHello")
    @Retry(name = "helloService")
    public String generateHello(String language, int formalityLevel) {
        log.info("Generating hello in language: {} with formality level: {}", language, formalityLevel);
        
        // Select appropriate strategy using factory
        HelloGenerationStrategy strategy = helloStrategyFactory.getStrategy(language, formalityLevel);
        
        // Generate the greeting
        String greeting = strategy.generateHello(null, language);
        
        // Publish event
        eventPublisher.publishEvent(new HelloGeneratedEvent(this, greeting, language, strategy.getStrategyName()));
        
        return greeting;
    }
    
    /**
     * Fallback method for generateHello in case of failures.
     *
     * @param language The language code
     * @param formalityLevel The formality level
     * @param e The exception that triggered the fallback
     * @return A fallback greeting
     */
    private String fallbackGenerateHello(String language, int formalityLevel, Exception e) {
        log.warn("Fallback method invoked for generateHello due to: {}", e.getMessage());
        return "Hello (fallback)";
    }
}
