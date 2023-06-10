package com.elsprage.learning.common.mapper;

import com.elsprage.learning.model.dto.GameResultDTO;
import com.elsprage.learning.persistance.entity.GameResult;

import java.time.LocalDateTime;

public final class GameResultMapper {

    public static GameResult mapToGameResult(final GameResultDTO gameResultDTO, final Long userId) {
        return GameResult.builder()
                .score(gameResultDTO.getScore())
                .date(LocalDateTime.now())
                .gameType(gameResultDTO.getGameType())
                .userId(userId)
                .packetId(gameResultDTO.getPacketId())
                .build();
    }
}
