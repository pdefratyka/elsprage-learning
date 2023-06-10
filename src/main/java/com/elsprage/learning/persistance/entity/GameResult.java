package com.elsprage.learning.persistance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "game_result")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GameResult {
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
    @Column(name = "game_type")
    private String gameType;
}
