package com.elsprage.learning.common.enumeration;

public enum LearningMode {
    VALUE_TO_TRANSLATION("VALUE_TO_TRANSLATION"),
    TRANSLATION_TO_VALUE("TRANSLATION_TO_VALUE");

    private String value;

    LearningMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static LearningMode fromValue(String value) {
        for (LearningMode learningMode : LearningMode.values()) {
            if (learningMode.getValue().equals(value)) {
                return learningMode;
            }
        }
        throw new IllegalArgumentException("Unknown learning mode: " + value);
    }
}
