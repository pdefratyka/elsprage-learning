package com.elsprage.learning.service;

public interface JwtService {
    String extractUsername(String jwt);

    boolean isTokenValid(String token);

    Long extractUserId(String jwt);
}
