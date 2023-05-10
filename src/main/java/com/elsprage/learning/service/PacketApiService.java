package com.elsprage.learning.service;

import com.elsprage.external.api.elsprage.words.PacketDTO;

import java.util.List;

public interface PacketApiService {

    List<PacketDTO> getUsersPackets(String authToken);
    PacketDTO getPacket(String authToken, Long packetId);
}
