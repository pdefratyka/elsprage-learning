package com.elsprage.learning.service;

import com.elsprage.learning.common.enumeration.LearningMode;

import java.util.List;

public interface RepetitionService {
    List<Long> getWordsIdsForRepetition(String token, Long packetId, LearningMode learningMode);
}
