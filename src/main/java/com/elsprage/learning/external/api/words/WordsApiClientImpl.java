package com.elsprage.learning.external.api.words;

import com.elsprage.external.api.elsprage.words.PacketDTO;
import com.elsprage.external.api.elsprage.words.UsersPacketsResponse;
import com.elsprage.learning.external.api.WebClientUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
@Slf4j
public class WordsApiClientImpl implements WordsApiClient {

    private final WebClient webClient;
    private static final int TIMEOUT = 30000;

    public WordsApiClientImpl(final WordsApiProperties wordsApiProperties, final ClientHttpConnector clientHttpConnector) {
        final ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        this.webClient = WebClientUtils.createWebClientBuilder(wordsApiProperties, log, clientHttpConnector,
                objectMapper).build();
    }

    @Override
    public UsersPacketsResponse getUsersPackets(final String authToken) {
        final WebClient.RequestHeadersSpec<?> requestHeadersSpec = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/packets")
                        .build())
                .header("Authorization", authToken)
                .accept(MediaType.APPLICATION_JSON);
        try {
            return requestHeadersSpec.retrieve().bodyToMono(new ParameterizedTypeReference<UsersPacketsResponse>() {
            }).timeout(Duration.ofMillis(TIMEOUT)).block();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public PacketDTO getPacket(String authToken, Long packetId) {
        final WebClient.RequestHeadersSpec<?> requestHeadersSpec = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/packets/" + packetId)
                        .build())
                .header("Authorization", authToken)
                .accept(MediaType.APPLICATION_JSON);
        try {
            return requestHeadersSpec.retrieve().bodyToMono(new ParameterizedTypeReference<PacketDTO>() {
            }).timeout(Duration.ofMillis(TIMEOUT)).block();
        } catch (Exception e) {
            //TODO Custom exception
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
