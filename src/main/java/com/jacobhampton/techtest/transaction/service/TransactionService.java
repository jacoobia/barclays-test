package com.jacobhampton.techtest.transaction.service;

import com.jacobhampton.techtest.account.service.AccountService;
import com.jacobhampton.techtest.auth.context.AuthContext;
import com.jacobhampton.techtest.shared.exception.AccessDeniedException;
import com.jacobhampton.techtest.shared.exception.InsufficientFundsException;
import com.jacobhampton.techtest.shared.exception.ResourceNotFoundException;
import com.jacobhampton.techtest.transaction.dto.CreateTransactionRequestDto;
import com.jacobhampton.techtest.transaction.dto.TransactionResponseDto;
import com.jacobhampton.techtest.transaction.model.Transaction;
import com.jacobhampton.techtest.transaction.repo.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public TransactionResponseDto createTransaction(String accountNumber, CreateTransactionRequestDto request) {
        accountChecks(accountNumber);

        AuthContext authContext = AuthContext.get();

        switch (request.type()) {
            case DEPOSIT ->createDepositTransaction(accountNumber, request);
            case WITHDRAWAL -> createWithdrawalTransaction(accountNumber, request);
        }

        Transaction transaction = transactionRepository.save(new Transaction(
                null,
                authContext.getUserId(),
                accountNumber,
                request.amount(),
                request.currency(),
                request.type(),
                request.reference(),
                Instant.now()
        ));
        return TransactionResponseDto.from(transaction);
    }

    private void createDepositTransaction(String accountNumber, CreateTransactionRequestDto request) {
        double balance = accountService.getBalance(accountNumber);
        accountService.updateBalance(accountNumber, balance + request.amount());
    }

    private void createWithdrawalTransaction(String accountNumber, CreateTransactionRequestDto request) {
        double balance = accountService.getBalance(accountNumber);
        if (balance < request.amount()) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        accountService.updateBalance(accountNumber, balance - request.amount());
    }

    public List<TransactionResponseDto> getTransactions(String accountNumber) {
        accountChecks(accountNumber);

        AuthContext authContext = AuthContext.get();

        return transactionRepository.findByUserIdAndAccountNumber(authContext.getUserId(), accountNumber)
                .stream()
                .map(TransactionResponseDto::from)
                .toList();
    }

    public TransactionResponseDto getTransaction(String accountNumber, String transactionId) {
        accountChecks(accountNumber);

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
        return TransactionResponseDto.from(transaction);
    }

    private void accountChecks(String accountNumber) {
        if(!accountService.accountExists(accountNumber)) {
            throw new ResourceNotFoundException("Account does not exist");
        }

        if(!accountService.isAccountOwner(accountNumber)) {
            throw new AccessDeniedException("Unauthorized");
        }
    }

}
