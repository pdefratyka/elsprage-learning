package com.elsprage.learning.service;

import com.elsprage.learning.model.dto.GameResultDTO;
import com.elsprage.learning.model.response.GameResultResponse;

public interface GameResultService {
    GameResultResponse saveGameResult(String token, GameResultDTO gameResultDTO);
}
