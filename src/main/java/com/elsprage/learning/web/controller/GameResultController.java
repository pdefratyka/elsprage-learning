package com.elsprage.learning.web.controller;

import com.elsprage.learning.model.dto.GamePacketDTO;
import com.elsprage.learning.model.dto.GameResultDTO;
import com.elsprage.learning.model.response.GamePacketsResponse;
import com.elsprage.learning.model.response.GameResultResponse;
import com.elsprage.learning.service.GameResultService;
import com.elsprage.learning.service.GameService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
@AllArgsConstructor
@Log4j2
public class GameResultController {

    private final GameResultService gameResultService;
    private final GameService gameService;

    @PostMapping("/result")
    public ResponseEntity<GameResultResponse> saveGameResult(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                             @RequestBody GameResultDTO gameResultDTO) {
        log.info("Save game result");
        final GameResultResponse gameResultResponse = gameResultService.saveGameResult(token, gameResultDTO);
        return ResponseEntity.ok(gameResultResponse);
    }

    @GetMapping("/{gameType}/packets")
    public ResponseEntity<GamePacketsResponse> getUsersGamePackets(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String gameType) {
        log.info("Get game packets for user");
        final List<GamePacketDTO> gamePackets = gameService.getGamePackets(token, gameType);
        final GamePacketsResponse gamePacketsResponse = new GamePacketsResponse(gamePackets);
        return ResponseEntity.ok(gamePacketsResponse);
    }
}
