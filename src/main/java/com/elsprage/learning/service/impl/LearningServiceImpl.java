package com.elsprage.learning.service.impl;

import com.elsprage.external.api.elsprage.words.PacketDTO;
import com.elsprage.external.api.elsprage.words.WordDTO;
import com.elsprage.learning.common.enumeration.LearningMode;
import com.elsprage.learning.model.dto.LearningPacketDTO;
import com.elsprage.learning.model.dto.LearningResultDTO;
import com.elsprage.learning.model.dto.LearningWordDTO;
import com.elsprage.learning.model.response.LearningResultResponse;
import com.elsprage.learning.persistance.entity.LearningResult;
import com.elsprage.learning.service.JwtService;
import com.elsprage.learning.service.LearningResultService;
import com.elsprage.learning.service.LearningService;
import com.elsprage.learning.service.PacketApiService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class LearningServiceImpl implements LearningService {

    private final PacketApiService packetApiService;
    private final LearningResultService learningResultService;
    private final JwtService jwtService;

    @Override
    public List<LearningPacketDTO> getLearningPacketsForUser(final String token) {
        final Long userId = jwtService.extractUserId(token);
        log.info("Get learning packets for user with id: {}", userId);
        final Set<LearningResult> learningResults = learningResultService.getUsersLearningResults(userId);
        final List<PacketDTO> usersPackets = packetApiService.getUsersPackets(token);
        return getLearningPackets(usersPackets, learningResults);
    }

    @Override
    public List<LearningWordDTO> getWordsForPacket(String token, Long packetId, LearningMode learningMode) {
        final PacketDTO packetDTO = packetApiService.getPacket(token, packetId);
        final List<WordDTO> words = packetDTO.getWords();
        final List<LearningWordDTO> learningWords = mapLearningWords(words, learningMode);
        Collections.shuffle(learningWords);
        return learningWords;
    }

    @Override
    public LearningResultResponse saveLearningResult(String token, LearningResultDTO learningResultDTO) {
        final Long userId = jwtService.extractUserId(token);
        log.info("Save learning result for user with id: {} and packetId: {}", userId, learningResultDTO.getPacketId());
        final LearningResult learningResult = learningResultService.saveLearningResult(userId, learningResultDTO);
        return LearningResultResponse.builder()
                .id(learningResult.getId())
                .userId(learningResult.getUserId())
                .packetId(learningResult.getPacketId())
                .score(learningResult.getScore())
                .date(learningResult.getDate())
                .build();
    }

    private List<LearningPacketDTO> getLearningPackets(final List<PacketDTO> usersPackets, final Set<LearningResult> learningResults) {
        return usersPackets.stream().map(packet -> getLearningPacket(packet, learningResults)).toList();
    }

    private LearningPacketDTO getLearningPacket(final PacketDTO packet, final Set<LearningResult> learningResults) {
        final LearningResult lastLearningResult = getLastLearningResult(packet.getId(), learningResults);
        final BigDecimal bestScore = getBestScore(packet.getId(), learningResults);
        return LearningPacketDTO.builder()
                .packetId(packet.getId())
                .packetName(packet.getName())
                .valueLanguage(packet.getValueLanguage().getSymbol())
                .translationLanguage(packet.getTranslationLanguage().getSymbol())
                .wordsCount(packet.getWordsIds().size())
                .lastLearned(lastLearningResult.getDate())
                .lastScore(lastLearningResult.getScore())
                .bestScore(bestScore)
                .build();
    }

    private LearningResult getLastLearningResult(final Long packetId, final Set<LearningResult> learningResults) {
        return learningResults.stream()
                .filter(learningResult -> learningResult.getPacketId().equals(packetId))
                .max(Comparator.comparing(LearningResult::getDate))
                .orElse(new LearningResult());
    }

    private BigDecimal getBestScore(final Long packetId, final Set<LearningResult> learningResults) {
        return learningResults.stream()
                .filter(learningResult -> learningResult.getPacketId().equals(packetId))
                .map(LearningResult::getScore)
                .max(BigDecimal::compareTo)
                .orElse(null);
    }

    private List<LearningWordDTO> mapLearningWords(final List<WordDTO> words, final LearningMode learningMode) {
        if (learningMode.equals(LearningMode.TRANSLATION_TO_VALUE)) {
            return words.stream().map(word -> LearningWordDTO.builder()
                    .wordId(word.getId())
                    .value(word.getTranslation())
                    .answer(word.getValue())
                    .example(word.getExample())
                    .sound(word.getSound())
                    .image(word.getImageDataEncoded())
                    .build()).collect(Collectors.toList());
        }
        return words.stream().map(word -> LearningWordDTO.builder()
                .wordId(word.getId())
                .value(word.getValue())
                .answer(word.getTranslation())
                .example(word.getExample())
                .sound(word.getSound())
                .image(word.getImageDataEncoded())
                .build()).collect(Collectors.toList());
    }
}
