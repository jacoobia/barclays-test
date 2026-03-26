package com.jacobhampton.techtest.auth.dto;

public record LoginRequestDto(
        String email,
        String password
) {
}
