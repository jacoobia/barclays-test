package com.jacobhampton.techtest.account.controller;

import com.jacobhampton.techtest.account.dto.AccountResponseDto;
import com.jacobhampton.techtest.account.dto.CreateAccountRequestDto;
import com.jacobhampton.techtest.account.service.AccountService;
import com.jacobhampton.techtest.auth.annotation.Private;
import jakarta.validation.Valid;
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
    public ResponseEntity<AccountResponseDto> createAccount(@Valid @RequestBody CreateAccountRequestDto body) {
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

}
