package com.jacobhampton.techtest.account.repo;

import com.jacobhampton.techtest.account.model.Account;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, String> {

    List<Account> findByUserId(String userId);

    Optional<Account> findByAccountNumber(String accountNumber);
}
