package com.elsprage.learning.service;

import com.elsprage.external.api.elsprage.words.PacketDTO;
import com.elsprage.learning.model.dto.LearningPacketsFilter;

import java.util.List;

public interface PacketsFilterService {
    List<PacketDTO> filterPackets(List<PacketDTO> packets, LearningPacketsFilter learningPacketsFilter);
}
