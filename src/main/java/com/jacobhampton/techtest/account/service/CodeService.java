package com.jacobhampton.techtest.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class CodeService {

    @Value("${app.sort-code.barclays-code}")
    private String barclaysCode;

    @Value("${app.sort-code.branch-code}")
    private String branchCode;

    private static final SecureRandom secureRandom = new SecureRandom();

    /**
     * TODO: Replace this with a better solution once I get a few minutes to read up on it,
     *       I found online that a checksum digit is appended to catch most user input errors before ever needing to query the db?? pretty cool
     *       https://en.wikipedia.org/wiki/Luhn_algorithm
     */
    public String generateAccountNumber() {
        long number = (long) (secureRandom.nextDouble() * 1_000_000);
        return String.format("%08d", number);
    }

    public String getSortCode() {
        return String.format("%s-%s", barclaysCode, branchCode);
    }


}
