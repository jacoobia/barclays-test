package com.jacobhampton.techtest.account.dto;

import com.jacobhampton.techtest.account.model.Account;
import com.jacobhampton.techtest.account.model.AccountType;
import com.jacobhampton.techtest.account.model.Currency;

public record AccountResponseDto(
        String accountNumber,
        String sortCode,
        String name,
        AccountType accountType,
        double balance,
        Currency currency,
        String createdTimestamp,
        String updatedTimestamp
) {

    public static AccountResponseDto from(Account account) {
        return new AccountResponseDto(
                account.getAccountNumber(),
                account.getSortCode(),
                account.getName(),
                account.getAccountType(),
                account.getBalance(),
                account.getCurrency(),
                account.getCreatedTimestamp().toString(),
                account.getUpdatedTimestamp().toString()
        );
    }

}
