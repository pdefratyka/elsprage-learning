package com.elsprage.learning.persistance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "learning_result")
@NoArgsConstructor
@AllArgsConstructor
@Data
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
}


