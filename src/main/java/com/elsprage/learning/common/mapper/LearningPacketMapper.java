package com.elsprage.learning.common.mapper;

import com.elsprage.external.api.elsprage.words.PacketDTO;
import com.elsprage.learning.model.dto.LearningPacketDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class LearningPacketMapper {

    public static LearningPacketDTO mapToLearningPacketDTO(final PacketDTO packetDTO, final LocalDateTime lastLearned,
                                                           final BigDecimal lastScore, final BigDecimal bestScore) {
        return LearningPacketDTO.builder()
                .packetId(packetDTO.getId())
                .packetName(packetDTO.getName())
                .valueLanguage(packetDTO.getValueLanguage().getSymbol())
                .translationLanguage(packetDTO.getTranslationLanguage().getSymbol())
                .wordsCount(packetDTO.getWordsIds().size())
                .lastLearned(lastLearned)
                .lastScore(lastScore)
                .bestScore(bestScore)
                .build();
    }
}
