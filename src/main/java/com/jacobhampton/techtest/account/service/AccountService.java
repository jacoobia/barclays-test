package com.jacobhampton.techtest.account.service;

import com.jacobhampton.techtest.account.dto.AccountResponseDto;
import com.jacobhampton.techtest.account.dto.CreateAccountRequestDto;
import com.jacobhampton.techtest.account.dto.UpdateAccountRequestDto;
import com.jacobhampton.techtest.account.model.Account;
import com.jacobhampton.techtest.account.model.Currency;
import com.jacobhampton.techtest.account.repo.AccountRepository;
import com.jacobhampton.techtest.auth.context.AuthContext;
import com.jacobhampton.techtest.shared.exception.AccessDeniedException;
import com.jacobhampton.techtest.shared.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final CodeService codeService;
    private final AccountRepository accountRepository;

    public AccountResponseDto createAccount(@Valid CreateAccountRequestDto request) {
        AuthContext authContext = AuthContext.get();

        Instant now = Instant.now();

        String accountNumber = codeService.generateAccountNumber();
        String sortCode = codeService.getSortCode();

        Account account = accountRepository.save(new Account(
                null,
                authContext.getUserId(),
                accountNumber,
                sortCode,
                request.name(),
                request.accountType(),
                0,
                Currency.GBP,
                now,
                now
        ));
        return AccountResponseDto.from(account);
    }

    public List<AccountResponseDto> getAccounts() {
        AuthContext authContext = AuthContext.get();
        return accountRepository.findByUserId(authContext.getUserId())
                .stream()
                .map(AccountResponseDto::from)
                .toList();
    }

    public boolean hasAccount() {
        AuthContext authContext = AuthContext.get();
        return !accountRepository.findByUserId(authContext.getUserId()).isEmpty();
    }

    /**
     * Originally I had added this has account check before reading further and seeing the scenario:
     * "User wants to fetch another user's bank account details" which should return forbidden not 404
     * if(!hasAccount())  throw new ResourceNotFoundException("You have no accounts");
     */
    public AccountResponseDto getAccount(String accountNumber) {
        AuthContext authContext = AuthContext.get();
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if(!account.getUserId().equals(authContext.getUserId())) {
            throw new AccessDeniedException("Unauthorized");
        }

        return AccountResponseDto.from(account);
    }

    public AccountResponseDto updateAccount(String accountNumber, @Valid UpdateAccountRequestDto request) {
        AuthContext authContext = AuthContext.get();
        Account existingAccount = accountRepository.findByAccountNumber(accountNumber)
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if(!existingAccount.getUserId().equals(authContext.getUserId())) {
            throw new AccessDeniedException("Unauthorized");
        }

        if (request.name() != null) existingAccount.setName(request.name());
        if (request.accountType() != null) existingAccount.setAccountType(request.accountType());

        existingAccount.setUpdatedTimestamp(Instant.now());

        Account updated = accountRepository.save(existingAccount);
        return AccountResponseDto.from(updated);
    }

    public void deleteAccount(String accountNumber) {
        AuthContext authContext = AuthContext.get();
        Account existingAccount = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if(!existingAccount.getUserId().equals(authContext.getUserId())) {
            throw new AccessDeniedException("Unauthorized");
        }

        accountRepository.delete(existingAccount);
    }

}
