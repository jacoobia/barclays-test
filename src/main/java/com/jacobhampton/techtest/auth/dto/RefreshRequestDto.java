package com.jacobhampton.techtest.auth.dto;

/**
 * Normally this would come from the client as a cookie!!!
 * But time is short and I don't know if the scenario is web only or mobile too so just body for now
 * @param refreshToken
 */
public record RefreshRequestDto(
        String refreshToken
) {
}
