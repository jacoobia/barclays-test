package com.jacobhampton.techtest.auth.controller;

import com.jacobhampton.techtest.auth.dto.AuthResponseDto;
import com.jacobhampton.techtest.auth.dto.LoginRequestDto;
import com.jacobhampton.techtest.auth.dto.RefreshRequestDto;
import com.jacobhampton.techtest.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerSpec {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto body) {
        log.debug("Received login request");
        return ResponseEntity.ok(authService.login(body));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDto> refresh(@RequestBody RefreshRequestDto body) {
        log.debug("Received refresh tokens request");
        return ResponseEntity.ok(authService.refreshTokens(body));
    }

}
