package com.jacobhampton.techtest.account.dto;

import com.jacobhampton.techtest.account.model.AccountType;
import jakarta.validation.constraints.NotNull;

public record CreateAccountRequestDto(
        @NotNull(message = "Name is required")
        String name,
        @NotNull(message = "Account type is required")
        AccountType accountType
) {
}
