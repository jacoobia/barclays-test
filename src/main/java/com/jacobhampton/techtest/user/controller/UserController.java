package com.jacobhampton.techtest.user.controller;

import com.jacobhampton.techtest.auth.annotation.Private;
import com.jacobhampton.techtest.user.dto.CreateUserRequestDto;
import com.jacobhampton.techtest.user.dto.UserResponseDto;
import com.jacobhampton.techtest.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController implements UserControllerSpec {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody CreateUserRequestDto body) {
        UserResponseDto response = userService.createUser(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Private
    @GetMapping("{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable String userId) {
        UserResponseDto response = userService.getUser(userId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("{userId}")
    public void updateUser(@PathVariable String userId) {

    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable String userId) {

    }

}
