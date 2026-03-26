package com.jacobhampton.techtest.user.service;

import com.jacobhampton.techtest.user.dto.CreateUserRequestDto;
import com.jacobhampton.techtest.user.dto.UserResponseDto;
import com.jacobhampton.techtest.user.model.User;
import com.jacobhampton.techtest.user.repo.UserRepository;
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
        return userRepository.findById(userId)
                .map(UserResponseDto::from)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
