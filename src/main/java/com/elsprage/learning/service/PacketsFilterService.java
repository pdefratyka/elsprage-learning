package com.elsprage.learning.service;

import com.elsprage.learning.model.dto.LearningPacketDTO;
import com.elsprage.learning.model.dto.LearningPacketsFilter;

import java.util.List;

public interface PacketsFilterService {
    List<LearningPacketDTO> filterPackets(List<LearningPacketDTO> packets, LearningPacketsFilter learningPacketsFilter);
}
