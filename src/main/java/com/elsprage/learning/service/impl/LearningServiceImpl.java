package com.elsprage.learning.service.impl;

import com.elsprage.external.api.elsprage.words.PacketDTO;
import com.elsprage.external.api.elsprage.words.WordDTO;
import com.elsprage.learning.common.enumeration.LearningMode;
import com.elsprage.learning.model.dto.LearningPacketDTO;
import com.elsprage.learning.model.dto.LearningResultDTO;
import com.elsprage.learning.model.dto.LearningWordDTO;
import com.elsprage.learning.model.response.LearningResultResponse;
import com.elsprage.learning.persistance.entity.LearningResult;
import com.elsprage.learning.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor
public class LearningServiceImpl implements LearningService {

    private final PacketApiService packetApiService;
    private final LearningResultService learningResultService;
    private final JwtService jwtService;
    private final RepetitionService repetitionService;

    @Override
    public List<LearningPacketDTO> getLearningPacketsForUser(final String token) {
        final Long userId = jwtService.extractUserId(token);
        log.info("Get learning packets for user with id: {}", userId);
        final Set<LearningResult> learningResults = learningResultService.getUsersLearningResults(userId);
        final List<PacketDTO> usersPackets = packetApiService.getUsersPackets(token);
        return getLearningPackets(usersPackets, learningResults);
    }

    @Override
    public List<LearningWordDTO> getWordsForPacket(String token, Long packetId, LearningMode learningMode, boolean isRepetition) {
        final PacketDTO packetDTO = packetApiService.getPacket(token, packetId);
        List<WordDTO> words = packetDTO.getWords();
        if (isRepetition) {
            words = filterToRepetitionWords(token, packetId, learningMode, words);
        }
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
        final Long numberOfRepetitionsForValueToTranslation = numberOfRepetitionsForValueToTranslation(packet.getId(), learningResults);
        final Long numberOfRepetitionsForTranslationToValue = numberOfRepetitionsForTranslationToValue(packet.getId(), learningResults);
        return LearningPacketDTO.builder()
                .packetId(packet.getId())
                .packetName(packet.getName())
                .valueLanguage(packet.getValueLanguage().getSymbol())
                .translationLanguage(packet.getTranslationLanguage().getSymbol())
                .wordsCount(packet.getWordsIds().size())
                .lastLearned(lastLearningResult.getDate())
                .lastScore(lastLearningResult.getScore())
                .bestScore(bestScore)
                .numberOfRepetitionsOfTranslationToValue(numberOfRepetitionsForTranslationToValue)
                .numberOfRepetitionsOfValueToTranslation(numberOfRepetitionsForValueToTranslation)
                .build();
    }

    private LearningResult getLastLearningResult(final Long packetId, final Set<LearningResult> learningResults) {
        return learningResults.stream()
                .filter(learningResult -> learningResult.getPacketId().equals(packetId))
                .filter(learningResult -> !learningResult.isRepetition())
                .max(Comparator.comparing(LearningResult::getDate))
                .orElse(new LearningResult());
    }

    private BigDecimal getBestScore(final Long packetId, final Set<LearningResult> learningResults) {
        return learningResults.stream()
                .filter(learningResult -> learningResult.getPacketId().equals(packetId))
                .filter(learningResult -> !learningResult.isRepetition())
                .map(LearningResult::getScore)
                .max(BigDecimal::compareTo)
                .orElse(null);
    }

    private Long numberOfRepetitionsForValueToTranslation(final Long packetId, final Set<LearningResult> learningResults) {
        return learningResults.stream()
                .filter(learningResult -> learningResult.getPacketId().equals(packetId))
                .filter(learningResult -> LearningMode.VALUE_TO_TRANSLATION.getValue().equals(learningResult.getLearningMode()))
                .max(Comparator.comparing(LearningResult::getDate))
                .map(learningResult -> learningResult.getWordRepetitions().stream().filter(wr -> wr.getNumberOfRepetitions() > 0).count())
                .orElse(null);
    }

    private Long numberOfRepetitionsForTranslationToValue(final Long packetId, final Set<LearningResult> learningResults) {
        return learningResults.stream()
                .filter(learningResult -> learningResult.getPacketId().equals(packetId))
                .filter(learningResult -> LearningMode.TRANSLATION_TO_VALUE.getValue().equals(learningResult.getLearningMode()))
                .max(Comparator.comparing(LearningResult::getDate))
                .map(learningResult -> learningResult.getWordRepetitions().stream().filter(wr -> wr.getNumberOfRepetitions() > 0).count())
                .orElse(null);
    }

    private List<LearningWordDTO> mapLearningWords(final List<WordDTO> words, final LearningMode learningMode) {
        if (learningMode.equals(LearningMode.TRANSLATION_TO_VALUE)) {
            return words.stream().map(word -> LearningWordDTO.builder()
                    .wordId(word.getId())
                    .value(word.getTranslation())
                    .answer(word.getValue())
                    .example(word.getExample())
                    .sound(word.getAudioDataEncoded())
                    .image(word.getImageDataEncoded())
                    .build()).collect(Collectors.toList());
        }
        return words.stream().map(word -> LearningWordDTO.builder()
                .wordId(word.getId())
                .value(word.getValue())
                .answer(word.getTranslation())
                .example(word.getExample())
                .sound(word.getAudioDataEncoded())
                .image(word.getImageDataEncoded())
                .build()).collect(Collectors.toList());
    }

    private List<WordDTO> filterToRepetitionWords(final String token, final Long packetId, final LearningMode learningMode, final List<WordDTO> allWords) {
        final List<Long> wordsWithRepetitions = repetitionService.getWordsIdsForRepetition(token, packetId, learningMode);
        return allWords.stream()
                .filter(word -> wordsWithRepetitions.contains(word.getId()))
                .collect(Collectors.toList());
    }
}
