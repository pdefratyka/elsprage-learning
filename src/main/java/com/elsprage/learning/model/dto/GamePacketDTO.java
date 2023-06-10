package com.elsprage.learning.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
public class GamePacketDTO {
    private Long packetId;
    private String packetName;
    private String valueLanguage;
    private String translationLanguage;
    private int wordsCount;
    private LocalDateTime lastLearned;
    private BigDecimal lastScore;
    private BigDecimal bestScore;
}
