package com.jacobhampton.techtest.transaction.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public enum TransactionType {
    @JsonAlias({"deposit", "DEPOSIT"})
    DEPOSIT,
    @JsonAlias({"withdrawal", "WITHDRAWAL"})
    WITHDRAWAL
}
