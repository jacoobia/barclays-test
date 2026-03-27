package com.jacobhampton.techtest.transaction.controller;

import com.jacobhampton.techtest.auth.annotation.Private;
import com.jacobhampton.techtest.transaction.dto.CreateTransactionRequestDto;
import com.jacobhampton.techtest.transaction.dto.TransactionResponseDto;
import com.jacobhampton.techtest.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/v1/account/{accountNumber}/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @Private
    @PostMapping
    public ResponseEntity<TransactionResponseDto> createTransaction(@PathVariable String accountNumber,
                                                                    @RequestBody CreateTransactionRequestDto body) {
        log.debug("Received create transaction request");
        TransactionResponseDto response = transactionService.createTransaction(accountNumber, body);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Private
    @GetMapping
    public ResponseEntity<List<TransactionResponseDto>> getTransactions(@PathVariable String accountNumber) {
        log.debug("Received get transactions request");
        List<TransactionResponseDto> response = transactionService.getTransactions(accountNumber);
        return ResponseEntity.ok(response);
    }

    @Private
    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponseDto> getTransaction(@PathVariable String accountNumber,
                                                              @PathVariable String transactionId) {
        log.debug("Received get transaction request");
        TransactionResponseDto response = transactionService.getTransaction(accountNumber, transactionId);
        return ResponseEntity.ok(response);
    }

}
