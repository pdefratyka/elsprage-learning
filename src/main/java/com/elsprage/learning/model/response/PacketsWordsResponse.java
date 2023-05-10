package com.elsprage.learning.model.response;

import com.elsprage.learning.model.dto.LearningWordDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PacketsWordsResponse {
    private final List<LearningWordDTO> learningWords;
}
