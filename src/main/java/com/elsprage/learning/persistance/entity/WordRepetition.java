package com.elsprage.learning.persistance.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "word_repetition")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class WordRepetition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @ManyToOne
    @JoinColumn(name = "learning_result_id")
    private LearningResult learningResult;
    @Column(name = "word_id")
    private Long wordId;
    @Column(name = "number_of_repetitions")
    private Long numberOfRepetitions;
}
