package com.elsprage.learning.service.impl;

import com.elsprage.external.api.elsprage.words.PacketDTO;
import com.elsprage.learning.model.dto.GamePacketDTO;
import com.elsprage.learning.persistance.entity.GameResult;
import com.elsprage.learning.persistance.repository.GameResultRepository;
import com.elsprage.learning.service.GameService;
import com.elsprage.learning.service.JwtService;
import com.elsprage.learning.service.PacketApiService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
@Log4j2
public class GameServiceImpl implements GameService {

    private final PacketApiService packetApiService;
    private final JwtService jwtService;
    private final GameResultRepository gameResultRepository;

    @Override
    public List<GamePacketDTO> getGamePackets(String token, String gameType) {
        final Long userId = jwtService.extractUserId(token);
        log.info("Get game packets for user with id: {}", userId);
        final Set<GameResult> gameResults = gameResultRepository.findByUserIdAndGameType(userId, gameType);
        final List<PacketDTO> usersPackets = packetApiService.getUsersPackets(token);
        return getGamePackets(usersPackets, gameResults);
    }

    private List<GamePacketDTO> getGamePackets(List<PacketDTO> usersPackets, Set<GameResult> gameResults) {
        return usersPackets.stream()
                .map(packet -> getGamePacket(packet, gameResults))
                .toList();
    }

    private GamePacketDTO getGamePacket(final PacketDTO packetDTO, Set<GameResult> gameResults) {
        final GameResult lastGameResult = getLastScore(packetDTO.getId(), gameResults);
        final BigDecimal bestScore = getBestScore(packetDTO.getId(), gameResults);
        return GamePacketDTO.builder()
                .packetId(packetDTO.getId())
                .packetName(packetDTO.getName())
                .wordsCount(packetDTO.getWordsIds().size())
                .valueLanguage(packetDTO.getValueLanguage().getSymbol())
                .translationLanguage(packetDTO.getTranslationLanguage().getSymbol())
                .lastScore(lastGameResult.getScore())
                .bestScore(bestScore)
                .lastLearned(lastGameResult.getDate())
                .build();
    }

    private BigDecimal getBestScore(Long packetId, Set<GameResult> gameResults) {
        return gameResults.stream()
                .filter(gameResult -> gameResult.getPacketId().equals(packetId))
                .map(GameResult::getScore)
                .max(BigDecimal::compareTo)
                .orElse(null);
    }

    private GameResult getLastScore(Long packetId, Set<GameResult> gameResults) {
        return gameResults.stream()
                .filter(gameResult -> gameResult.getPacketId().equals(packetId))
                .max(Comparator.comparing(GameResult::getDate))
                .orElse(new GameResult());
    }
}
