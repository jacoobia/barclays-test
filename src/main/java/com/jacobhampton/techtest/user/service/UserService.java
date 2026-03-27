package com.jacobhampton.techtest.user.service;

import com.jacobhampton.techtest.account.service.AccountService;
import com.jacobhampton.techtest.auth.context.AuthContext;
import com.jacobhampton.techtest.shared.exception.AccessDeniedException;
import com.jacobhampton.techtest.shared.exception.ResourceNotFoundException;
import com.jacobhampton.techtest.user.dto.CreateUserRequestDto;
import com.jacobhampton.techtest.user.dto.UpdateUserRequestDto;
import com.jacobhampton.techtest.user.dto.UserResponseDto;
import com.jacobhampton.techtest.user.model.User;
import com.jacobhampton.techtest.user.repo.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;

    public UserResponseDto createUser(CreateUserRequestDto request) {
        Instant now = Instant.now();
        String hashedPassword = passwordEncoder.encode(request.password());
        User created = userRepository.save(new User(
                null,
                request.name(),
                hashedPassword,
                request.address(),
                request.phoneNumber(),
                request.email(),
                now,
                now
        ));
        return UserResponseDto.from(created);
    }

    public UserResponseDto getUser(String userId) {
        AuthContext authContext = AuthContext.get();
        if(!authContext.getUserId().equals(userId)) {
            throw new AccessDeniedException("Unauthorized");
        }

        return userRepository.findById(userId)
                .map(UserResponseDto::from)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserResponseDto updateUser(String userId, UpdateUserRequestDto body) {
        AuthContext authContext = AuthContext.get();
        if(!authContext.getUserId().equals(userId)) {
            throw new AccessDeniedException("Unauthorized");
        }

        User existingUser = userRepository.findById(authContext.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (body.name() != null) existingUser.setName(body.name());
        if (body.address() != null) existingUser.setAddress(body.address());
        if (body.phoneNumber() != null) existingUser.setPhoneNumber(body.phoneNumber());
        if (body.email() != null) existingUser.setEmail(body.email());

        existingUser.setUpdatedTimestamp(Instant.now());

        User updated = userRepository.save(existingUser);
        return UserResponseDto.from(updated);
    }

    public void deleteUser(@Valid @NotNull String userId) {
        AuthContext authContext = AuthContext.get();
        if(!authContext.getUserId().equals(userId)) {
            throw new AccessDeniedException("Unauthorized");
        }

        if(accountService.hasAccount()) {
            throw new AccessDeniedException("Cannot delete user with existing accounts");
        }

        User existingUser = userRepository.findById(authContext.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepository.delete(existingUser);
    }
}
