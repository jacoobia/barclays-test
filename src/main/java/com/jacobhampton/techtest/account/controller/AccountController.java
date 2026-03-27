package com.jacobhampton.techtest.account.controller;

import com.jacobhampton.techtest.account.dto.AccountResponseDto;
import com.jacobhampton.techtest.account.dto.CreateAccountRequestDto;
import com.jacobhampton.techtest.account.dto.UpdateAccountRequestDto;
import com.jacobhampton.techtest.account.service.AccountService;
import com.jacobhampton.techtest.auth.annotation.Private;
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
@RequestMapping("/v1/account")
@RequiredArgsConstructor
public class AccountController implements AccountControllerSpec {

    private final AccountService accountService;

    @Private
    @PostMapping
    public ResponseEntity<AccountResponseDto> createAccount(@RequestBody CreateAccountRequestDto body) {
        log.debug("Received create account request");
        AccountResponseDto response = accountService.createAccount(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Private
    @GetMapping
    public ResponseEntity<List<AccountResponseDto>> getAccounts() {
        log.debug("Received get accounts request");
        List<AccountResponseDto> response = accountService.getAccounts();
        return ResponseEntity.ok(response);
    }

    @Private
    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponseDto> getAccount(@PathVariable String accountNumber) {
        log.debug("Received get account request");
        AccountResponseDto response = accountService.getAccount(accountNumber);
        return ResponseEntity.ok(response);
    }

    @Private
    @PatchMapping("/{accountNumber}")
    public ResponseEntity<AccountResponseDto> updateAccount(@PathVariable String accountNumber,
                                                            @RequestBody UpdateAccountRequestDto body) {
        log.debug("Received update account request");
        AccountResponseDto response = accountService.updateAccount(accountNumber, body);
        return ResponseEntity.ok(response);
    }

    @Private
    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String accountNumber) {
        log.debug("Received delete account request");
        accountService.deleteAccount(accountNumber);
        return ResponseEntity.noContent().build();
    }

}
