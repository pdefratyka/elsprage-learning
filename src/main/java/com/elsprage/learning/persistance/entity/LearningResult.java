package com.elsprage.learning.persistance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "learning_result")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LearningResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private Long userId;
    @Column
    private Long packetId;
    @Column
    private LocalDateTime date;
    @Column
    private BigDecimal score;
    @Column(name = "learning_mode")
    private String learningMode;
    @Column(name = "is_repetition")
    private boolean isRepetition;
    @OneToMany(mappedBy = "learningResult", cascade = CascadeType.ALL)
    private Set<WordRepetition> wordRepetitions;
}


