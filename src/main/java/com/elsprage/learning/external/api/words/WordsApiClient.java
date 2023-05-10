package com.elsprage.learning.external.api.words;


import com.elsprage.external.api.elsprage.words.PacketDTO;
import com.elsprage.external.api.elsprage.words.UsersPacketsResponse;

public interface WordsApiClient {
    UsersPacketsResponse getUsersPackets(String authToken);

    PacketDTO getPacket(String authToken, Long packetId);
}
