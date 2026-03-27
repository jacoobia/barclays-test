package com.jacobhampton.techtest.account.dto;

import com.jacobhampton.techtest.account.model.AccountType;
import com.jacobhampton.techtest.account.model.Currency;

public record AccountResponseDto(
        String accountNumber,
        String sortCode,
        String name,
        AccountType accountType,
        int balance,
        Currency currency,
        String createdTimestamp,
        String updatedTimestamp
) {
}
