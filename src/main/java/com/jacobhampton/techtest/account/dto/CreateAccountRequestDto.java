package com.jacobhampton.techtest.account.dto;

import com.jacobhampton.techtest.account.model.AccountType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateAccountRequestDto(
        @NotNull(message = "Name is required")
        @Size(max = 15, message = "Names must be at most 10 characters")
        String name,
        @NotNull(message = "Account type is required")
        AccountType accountType
) {
}
