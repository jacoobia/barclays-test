package com.jacobhampton.techtest.user.controller;

import com.jacobhampton.techtest.auth.annotation.Private;
import com.jacobhampton.techtest.user.dto.CreateUserRequestDto;
import com.jacobhampton.techtest.user.dto.UpdateUserRequestDto;
import com.jacobhampton.techtest.user.dto.UserResponseDto;
import com.jacobhampton.techtest.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController implements UserControllerSpec {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody CreateUserRequestDto body) {
        log.debug("Received create user request");
        UserResponseDto response = userService.createUser(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Private
    @GetMapping("{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable String userId) {
        log.debug("Received get use request");
        UserResponseDto response = userService.getUser(userId);
        return ResponseEntity.ok(response);
    }

    @Private
    @PatchMapping("{userId}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable String userId, @RequestBody UpdateUserRequestDto body) {
        log.debug("Received update user request");
        UserResponseDto response = userService.updateUser(userId, body);
        return ResponseEntity.ok(response);
    }

    @Private
    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        log.debug("Received delete user request");
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
