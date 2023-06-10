package com.elsprage.learning.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class GameResultResponse {
    private final Long id;
    private final Long userId;
    private final Long packetId;
    private final LocalDateTime date;
    private final BigDecimal score;
}
