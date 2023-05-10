package com.elsprage.learning.utils;

public final class TokenUtils {

    public static String getTokenFromHeader(final String authenticationHeader) {
        final int startOfTokenInHeader = 7;
        return authenticationHeader.substring(startOfTokenInHeader);
    }
}
