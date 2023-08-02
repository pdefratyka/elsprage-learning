package com.elsprage.learning.service.impl;

import com.elsprage.learning.model.dto.LearningPacketDTO;
import com.elsprage.learning.model.dto.LearningPacketsFilter;
import com.elsprage.learning.service.PacketsFilterService;
import io.micrometer.common.util.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class PacketsFilterServiceImpl implements PacketsFilterService {
    @Override
    public List<LearningPacketDTO> filterPackets(final List<LearningPacketDTO> packets, final LearningPacketsFilter learningPacketsFilter) {
        log.info("Filter packets by: {}", learningPacketsFilter);
        final List<LearningPacketDTO> packetsFilteredByLanguage = filterPacketsByLanguage(packets, learningPacketsFilter.getLanguage());
        final List<LearningPacketDTO> packetsFilteredByScore = filterPacketsByScore(packetsFilteredByLanguage, learningPacketsFilter.isScoreNot100());
        final List<LearningPacketDTO> packetFilteredByRepeats = filterPacketsByRepeats(packetsFilteredByScore, learningPacketsFilter.isHaveRepeats());
        return packetFilteredByRepeats;
    }

    private List<LearningPacketDTO> filterPacketsByLanguage(final List<LearningPacketDTO> packets, final String language) {
        if (StringUtils.isEmpty(language)) {
            return packets;
        }
        return packets.stream()
                .filter(packet -> language.equalsIgnoreCase(packet.getValueLanguage()))
                .collect(Collectors.toList());
    }

    private List<LearningPacketDTO> filterPacketsByScore(final List<LearningPacketDTO> packets, final boolean isScoreNot100) {
        if (!isScoreNot100) {
            return packets;
        }
        return packets.stream()
                .filter(packet -> {
                    if (packet.getLastScore() == null) {
                        return true;
                    }
                    return packet.getLastScore().intValue()!=100;
                }).collect(Collectors.toList());
    }

    private List<LearningPacketDTO> filterPacketsByRepeats(final List<LearningPacketDTO> packets, final boolean haveRepeats) {
        if (!haveRepeats) {
            return packets;
        }
        return packets.stream()
                .filter(packet -> {
                    if (packet.getNumberOfRepetitionsOfTranslationToValue() == null) {
                        return false;
                    }
                    return !packet.getNumberOfRepetitionsOfTranslationToValue().equals(0L);
                }).collect(Collectors.toList());
    }
}
