package com.elsprage.learning.service.impl;

import com.elsprage.learning.model.dto.LearningResultDTO;
import com.elsprage.learning.persistance.entity.LearningResult;
import com.elsprage.learning.persistance.repository.LearningResultRepository;
import com.elsprage.learning.service.LearningResultService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@AllArgsConstructor
public class LearningResultServiceImpl implements LearningResultService {

    private final LearningResultRepository learningResultRepository;

    @Override
    public Set<LearningResult> getUsersLearningResults(final Long userId) {
        return learningResultRepository.findByUserId(userId);
    }

    public LearningResult saveLearningResult(final Long userId, final LearningResultDTO learningResultDTO){
        final LearningResult learningresult=LearningResult.builder()
                .userId(userId)
                .packetId(learningResultDTO.getPacketId())
                .score(learningResultDTO.getScore())
                .date(LocalDateTime.now())
                .build();
        return learningResultRepository.save(learningresult);
    }
}
