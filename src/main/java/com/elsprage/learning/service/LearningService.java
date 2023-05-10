package com.elsprage.learning.service;

import com.elsprage.learning.common.enumeration.LearningMode;
import com.elsprage.learning.model.dto.LearningPacketDTO;
import com.elsprage.learning.model.dto.LearningResultDTO;
import com.elsprage.learning.model.dto.LearningWordDTO;
import com.elsprage.learning.model.response.LearningResultResponse;

import java.util.List;

public interface LearningService {
    List<LearningPacketDTO> getLearningPacketsForUser(String token);

    List<LearningWordDTO> getWordsForPacket(String token, Long packetId, LearningMode learningMode);

    LearningResultResponse saveLearningResult(String token, LearningResultDTO learningResultDTO);
}