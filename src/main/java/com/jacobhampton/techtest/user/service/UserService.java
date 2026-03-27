package com.jacobhampton.techtest.user.service;

import com.jacobhampton.techtest.auth.context.AuthContext;
import com.jacobhampton.techtest.shared.exception.AccessDeniedException;
import com.jacobhampton.techtest.shared.exception.UserNotFoundException;
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
        AuthContext authContext = AuthContext.get();
        if(!authContext.getUserId().equals(userId)) {
            throw new AccessDeniedException("Unauthorized");
        }

        return userRepository.findById(userId)
                .map(UserResponseDto::from)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

}
