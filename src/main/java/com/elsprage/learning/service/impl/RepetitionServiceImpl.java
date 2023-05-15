package com.elsprage.learning.service.impl;

import com.elsprage.learning.common.enumeration.LearningMode;
import com.elsprage.learning.persistance.entity.LearningResult;
import com.elsprage.learning.persistance.entity.WordRepetition;
import com.elsprage.learning.persistance.repository.LearningResultRepository;
import com.elsprage.learning.service.JwtService;
import com.elsprage.learning.service.RepetitionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RepetitionServiceImpl implements RepetitionService {

    private final LearningResultRepository learningResultRepository;
    private final JwtService jwtService;

    @Override
    public List<Long> getWordsIdsForRepetition(String token, Long packetId, LearningMode learningMode) {
        final Long userId = jwtService.extractUserId(token);
        final Optional<LearningResult> learningResult =
                learningResultRepository.getLastLearningResult(userId, packetId, learningMode.getValue());
        if (learningResult.isEmpty()) {
            throw new RuntimeException("No learning results for specified properties");
        }
        return learningResult.get().getWordRepetitions().stream()
                .filter(wordRepetition -> wordRepetition.getNumberOfRepetitions() > 0)
                .map(WordRepetition::getWordId)
                .toList();
    }
}
