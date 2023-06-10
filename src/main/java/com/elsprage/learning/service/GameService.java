package com.elsprage.learning.service;

import com.elsprage.learning.model.dto.GamePacketDTO;

import java.util.List;

public interface GameService {
    List<GamePacketDTO> getGamePackets(String token, String gameType);
}
