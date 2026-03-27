package com.jacobhampton.techtest.account.dto;

import com.jacobhampton.techtest.account.model.AccountType;
import jakarta.validation.constraints.NotNull;

public record UpdateAccountRequestDto(
        String name,
        AccountType accountType
) {
}
