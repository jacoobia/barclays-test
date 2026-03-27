package com.jacobhampton.techtest.user.dto;

import jakarta.validation.constraints.Pattern;

public record UpdateUserRequestDto(
        String name,
        UserAddressDto address,
        @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "Phone number must be in E.164 format")
        String phoneNumber,
        String email
) {
}
