package com.elsprage.learning.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LearningWordDTO {
    private final Long wordId;
    private final String value;
    private final String answer;
    private final String example;
    private final String image;
    private final String sound;
    private final String explanation;
}
