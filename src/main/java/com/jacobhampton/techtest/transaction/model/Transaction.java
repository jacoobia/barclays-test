package com.jacobhampton.techtest.transaction.model;

import com.jacobhampton.techtest.account.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@AllArgsConstructor
@Document(collection = "transactions")
public class Transaction {

    @Id
    private String id;

    private String userId;

    private String accountNumber;

    private double amount;

    private Currency currency;

    private TransactionType type;

    private String reference;

    @CreatedDate
    private Instant createdTimeStamp;

}
