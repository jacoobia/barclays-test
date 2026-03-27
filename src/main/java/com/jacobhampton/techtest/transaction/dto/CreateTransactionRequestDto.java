package com.jacobhampton.techtest.transaction.dto;

import com.jacobhampton.techtest.account.model.Currency;
import com.jacobhampton.techtest.transaction.model.TransactionType;
import jakarta.validation.constraints.NotNull;

public record CreateTransactionRequestDto(
        @NotNull(message = "Account number is required")
        double amount,
        @NotNull(message = "Currency is required")
        Currency currency,
        @NotNull(message = "Transaction type is required")
        TransactionType type,
        String reference
) {
}
