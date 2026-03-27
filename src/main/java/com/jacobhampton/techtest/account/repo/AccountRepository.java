package com.jacobhampton.techtest.account.repo;

import com.jacobhampton.techtest.account.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
}
