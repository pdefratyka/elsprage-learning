package com.elsprage.learning.model.response;

import com.elsprage.learning.model.dto.GamePacketDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GamePacketsResponse {
    private List<GamePacketDTO> gamePackets;
}
