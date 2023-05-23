package com.elsprage.learning.web.controller;

import com.elsprage.learning.common.enumeration.LearningMode;
import com.elsprage.learning.model.dto.LearningPacketDTO;
import com.elsprage.learning.model.dto.LearningResultDTO;
import com.elsprage.learning.model.dto.LearningWordDTO;
import com.elsprage.learning.model.response.LearningPacketResponse;
import com.elsprage.learning.model.response.LearningResultResponse;
import com.elsprage.learning.model.response.PacketsWordsResponse;
import com.elsprage.learning.service.LearningService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/learning")
@AllArgsConstructor
@Log4j2
public class LearningController {

    private final LearningService learningService;

    @GetMapping("/packets")
    public ResponseEntity<LearningPacketResponse> getUsersLearningPackets(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        log.info("Get learning packets for user");
        final List<LearningPacketDTO> learningPackets = learningService.getLearningPacketsForUser(token);
        final LearningPacketResponse learningPacketResponse = new LearningPacketResponse(learningPackets);
        return ResponseEntity.ok(learningPacketResponse);
    }

    @GetMapping("/{packetId}/words")
    public ResponseEntity<PacketsWordsResponse> getPacketsWords(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @PathVariable Long packetId,
            @RequestParam LearningMode learningMode,
            @RequestParam boolean repetitionMode
    ) {
        log.info("Get packets words for packetId: {} and mode: {} and repetitionMode:{}", packetId, learningMode, repetitionMode);
        final List<LearningWordDTO> words = learningService.getWordsForPacket(token, packetId, learningMode, repetitionMode);
        final PacketsWordsResponse packetsWordsResponse = new PacketsWordsResponse(words);
        return ResponseEntity.ok(packetsWordsResponse);
    }

    @PostMapping("/result")
    public ResponseEntity<LearningResultResponse> saveLearningResult(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                                     @RequestBody LearningResultDTO learningResultDTO) {
        log.info("Save learning result for user");
        final LearningResultResponse learningResultResponse = learningService.saveLearningResult(token, learningResultDTO);
        return ResponseEntity.ok(learningResultResponse);
    }
}
