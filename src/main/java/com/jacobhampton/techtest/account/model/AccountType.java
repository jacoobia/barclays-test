package com.jacobhampton.techtest.account.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum AccountType {
    @JsonAlias({"personal", "PERSONAL"})
    PERSONAL,
    @JsonAlias({"business", "BUSINESS"})
    BUSINESS
}
