package com.jacobhampton.techtest.transaction.dto;

import com.jacobhampton.techtest.account.model.Currency;
import com.jacobhampton.techtest.transaction.model.Transaction;
import com.jacobhampton.techtest.transaction.model.TransactionType;

public record TransactionResponseDto(
        String id,
        String accountNumber,
        double amount,
        Currency currency,
        TransactionType type,
        String reference,
        String userId,
        String createdTimeStamp
) {
    public static TransactionResponseDto from(Transaction transaction) {
        return new TransactionResponseDto(
                transaction.getId(),
                transaction.getAccountNumber(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getType(),
                transaction.getReference(),
                transaction.getUserId(),
                transaction.getCreatedTimeStamp().toString()
        );
    }
}
