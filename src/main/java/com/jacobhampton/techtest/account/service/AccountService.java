package com.jacobhampton.techtest.account.service;

import com.jacobhampton.techtest.account.dto.AccountResponseDto;
import com.jacobhampton.techtest.account.dto.CreateAccountRequestDto;
import com.jacobhampton.techtest.auth.context.AuthContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    @Value("${app.sort-code.barclays-code}")
    private String barclaysCode;

    @Value("${app.sort-code.branch-code}")
    private String branchCode;

    private static final SecureRandom secureRandom = new SecureRandom();

    public AccountResponseDto createAccount(@Valid CreateAccountRequestDto request) {
        AuthContext authContext = AuthContext.get();
        String accountNumber = generateAccountNumber();
        return null;
    }

    public List<AccountResponseDto> getAccounts() {
        AuthContext authContext = AuthContext.get();
        return null;
    }

    /**
     * TODO: Replace this with a better solution once I get a few minutes to read up on it,
     *       I found online that a checksum digit is appended to catch most user input errors before ever needing to query the db?? pretty cool
     *       https://en.wikipedia.org/wiki/Luhn_algorithm
     */
    private String generateAccountNumber() {
        long number = (long) (secureRandom.nextDouble() * 1_000_000);
        return String.format("%08d", number);
    }

    private String getSortCode() {
        return String.format("%s-%s", barclaysCode, branchCode);
    }

}
