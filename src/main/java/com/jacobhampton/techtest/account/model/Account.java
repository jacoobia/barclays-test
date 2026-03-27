package com.jacobhampton.techtest.account.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@AllArgsConstructor
@Document(collection = "accounts")
public class Account {

    @Id
    private String id;

    private String userId;

    @Indexed(unique = true)
    private String accountNumber;

    private String sortCode;

    private String name;

    private AccountType accountType;

    private double balance;

    private Currency currency;

    @CreatedDate
    private Instant createdTimestamp;

    @LastModifiedDate
    private Instant updatedTimestamp;

}
