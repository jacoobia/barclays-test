package com.jacobhampton.techtest.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateUserRequestDto(
        @NotNull(message = "Name is required")
        String name,
        @NotNull(message = "Address is required")
        UserAddressDto address,
        @NotNull(message = "Phone number is required")
        @Pattern(regexp = "^\\+[1-9]\\d{1,14}$")
        @NotNull(message = "Phone number must be in E.164 format")
        String phoneNumber,
        @NotNull(message = "Email is required")
        String email
) {
}
