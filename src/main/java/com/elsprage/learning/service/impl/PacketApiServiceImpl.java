package com.elsprage.learning.service.impl;

import com.elsprage.external.api.elsprage.words.PacketDTO;
import com.elsprage.external.api.elsprage.words.UsersPacketsResponse;
import com.elsprage.learning.external.api.words.WordsApiClient;
import com.elsprage.learning.service.PacketApiService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class PacketApiServiceImpl implements PacketApiService {

    private final WordsApiClient wordsApiClient;

    @Override
    public List<PacketDTO> getUsersPackets(final String authToken) {
        final UsersPacketsResponse usersPacketsResponse = wordsApiClient.getUsersPackets(authToken);
        return usersPacketsResponse.getPackets();
    }

    public PacketDTO getPacket(final String authToken, final Long packetId) {
        return wordsApiClient.getPacket(authToken, packetId);
    }
}
