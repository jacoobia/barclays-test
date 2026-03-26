package com.jacobhampton.techtest.auth.dto;

/**
 * Normally I would send these in HttpOnly cookies but for now, given idk if this scenario
 * is to reflect aa webapp, a mobile app or both I'll return them as a response body
 * @param accessToken
 * @param refreshToken
 */
public record AuthResponseDto(
        String accessToken,
        String refreshToken
) {
}
