package com.jacobhampton.techtest.user.dto;

import jakarta.validation.constraints.NotNull;

public record UserAddressDto(
        @NotNull(message = "Line 1 is required")
        String line1,
        String line2,
        String line3,
        @NotNull(message = "Town is required")
        String town,
        @NotNull(message = "County is required")
        String county,
        @NotNull(message = "Postcode is required")
        String postcode
) {
}
