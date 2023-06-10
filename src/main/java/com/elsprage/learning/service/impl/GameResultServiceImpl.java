package com.elsprage.learning.service.impl;

import com.elsprage.learning.common.mapper.GameResultMapper;
import com.elsprage.learning.model.dto.GameResultDTO;
import com.elsprage.learning.model.response.GameResultResponse;
import com.elsprage.learning.persistance.entity.GameResult;
import com.elsprage.learning.persistance.repository.GameResultRepository;
import com.elsprage.learning.service.GameResultService;
import com.elsprage.learning.service.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class GameResultServiceImpl implements GameResultService {

    private final GameResultRepository gameResultRepository;
    private final JwtService jwtService;

    @Override
    public GameResultResponse saveGameResult(String token, GameResultDTO gameResultDTO) {
        final Long userId = jwtService.extractUserId(token);
        log.info("Save game result for user with id: {} and packetId: {}", userId, gameResultDTO.getPacketId());
        final GameResult gameResult = gameResultRepository.save(GameResultMapper.mapToGameResult(gameResultDTO, userId));
        return GameResultResponse.builder()
                .id(gameResult.getId())
                .score(gameResult.getScore())
                .date(gameResult.getDate())
                .packetId(gameResult.getPacketId())
                .userId(gameResult.getPacketId())
                .build();
    }
}
