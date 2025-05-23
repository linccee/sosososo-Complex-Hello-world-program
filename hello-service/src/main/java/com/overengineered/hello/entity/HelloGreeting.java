package com.overengineered.hello.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a "Hello" greeting in the database.
 * Despite being a simple string, we've made it a full-fledged entity with numerous properties.
 */
@Entity
@Table(name = "hello_greetings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelloGreeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String uuid = UUID.randomUUID().toString();

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false)
    private Integer priority;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Version
    private Long version;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Formality formality;

    public enum Formality {
        CASUAL, FORMAL, VERY_FORMAL
    }
}
