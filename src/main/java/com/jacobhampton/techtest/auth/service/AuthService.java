package com.jacobhampton.techtest.auth.service;

import com.jacobhampton.techtest.auth.dto.AuthResponseDto;
import com.jacobhampton.techtest.auth.dto.LoginRequestDto;
import com.jacobhampton.techtest.auth.dto.RefreshRequestDto;
import com.jacobhampton.techtest.user.model.User;
import com.jacobhampton.techtest.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final TokenService tokenService;

    public AuthResponseDto login(LoginRequestDto request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        boolean passwordMatches = encoder.matches(request.password(), user.getPassword());
        if (!passwordMatches) {
            throw new RuntimeException("Invalid email or password");
        }

        return new AuthResponseDto(
                tokenService.generateAccessToken(user.getId()),
                tokenService.generateRefreshToken(user.getId())
        );
    }

    public AuthResponseDto refreshTokens(RefreshRequestDto request) {
        String refreshToken = request.refreshToken();

        if(!tokenService.validateToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String userId = tokenService.extractUserId(refreshToken);
        return new AuthResponseDto(
                tokenService.generateAccessToken(userId),
                tokenService.generateRefreshToken(userId)
        );
    }

}
