package com.jacobhampton.techtest.transaction.repo;

import com.jacobhampton.techtest.transaction.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

    List<Transaction> findByUserIdAndAccountNumber(String userId, String accountNumber);

}
