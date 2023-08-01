package com.elsprage.learning.service.impl;

import com.elsprage.external.api.elsprage.words.PacketDTO;
import com.elsprage.learning.model.dto.LearningPacketsFilter;
import com.elsprage.learning.service.PacketsFilterService;
import io.micrometer.common.util.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class PacketsFilterServiceImpl implements PacketsFilterService {
    @Override
    public List<PacketDTO> filterPackets(final List<PacketDTO> packets, final LearningPacketsFilter learningPacketsFilter) {
        log.info("Filter packets by: {}", learningPacketsFilter);
        return filterPacketsByLanguage(packets, learningPacketsFilter.getLanguage());
    }

    private List<PacketDTO> filterPacketsByLanguage(final List<PacketDTO> packets, final String language) {
        if (StringUtils.isEmpty(language)) {
            return packets;
        }
        return packets.stream()
                .filter(packet -> language.equalsIgnoreCase(packet.getValueLanguage().getSymbol()))
                .collect(Collectors.toList());
    }
}
