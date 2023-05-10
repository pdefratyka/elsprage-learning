package com.elsprage.learning.service;

import com.elsprage.learning.model.dto.LearningResultDTO;
import com.elsprage.learning.persistance.entity.LearningResult;

import java.util.Set;

public interface LearningResultService {
    Set<LearningResult> getUsersLearningResults(Long userId);

    LearningResult saveLearningResult(Long userId, LearningResultDTO learningResultDTO);
}
