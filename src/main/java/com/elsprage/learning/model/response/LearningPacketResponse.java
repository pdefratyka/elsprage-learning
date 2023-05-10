package com.elsprage.learning.model.response;

import com.elsprage.learning.model.dto.LearningPacketDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class LearningPacketResponse {
    private List<LearningPacketDTO> learningPackets;
}
