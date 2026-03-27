package com.jacobhampton.techtest.user.controller;

import com.jacobhampton.techtest.shared.dto.ErrorResponseDto;
import com.jacobhampton.techtest.user.dto.CreateUserRequestDto;
import com.jacobhampton.techtest.user.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Users", description = "User management endpoints")
public interface UserControllerSpec {

    @Operation(summary = "Create a new user", description = "Registers a new user account")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "User with this email already exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody CreateUserRequestDto body);

    @Operation(summary = "Get user by ID", description = "Retrieves a user by their unique identifier. Requires authentication.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User found",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized — missing or invalid access token",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    ResponseEntity<UserResponseDto> getUser(@Parameter(description = "User ID") @PathVariable String userId);

    @Operation(summary = "Update a user", description = "Partially updates an existing user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    void updateUser(@Parameter(description = "User ID") @PathVariable String userId);

    @Operation(summary = "Delete a user", description = "Deletes a user by their unique identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    void deleteUser(@Parameter(description = "User ID") @PathVariable String userId);

}

