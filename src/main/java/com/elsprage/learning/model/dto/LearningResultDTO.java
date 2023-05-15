package com.elsprage.learning.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LearningResultDTO {
    private Long packetId;
    private BigDecimal score;
    private Set<WordRepetitionDTO> repetitions;
    private String learningMode;
    private boolean repetition;
}
