package com.jacobhampton.techtest.auth.controller;

import com.jacobhampton.techtest.auth.dto.AuthResponseDto;
import com.jacobhampton.techtest.auth.dto.LoginRequestDto;
import com.jacobhampton.techtest.auth.dto.RefreshRequestDto;
import com.jacobhampton.techtest.shared.dto.ErrorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "Authentication endpoints")
public interface AuthControllerSpec {

    @Operation(summary = "Login", description = "Authenticates a user with email and password, returning access and refresh tokens")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Authentication successful",
                    content = @Content(schema = @Schema(implementation = AuthResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid email or password",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto body);

    @Operation(summary = "Refresh tokens", description = "Issues a new access and refresh token pair using a valid refresh token")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Tokens refreshed successfully",
                    content = @Content(schema = @Schema(implementation = AuthResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid or expired refresh token",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    ResponseEntity<AuthResponseDto> refresh(@RequestBody RefreshRequestDto body);

}

