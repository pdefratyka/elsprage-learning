package com.elsprage.learning.model.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LearningPacketsFilter {
    private final String language;
}
