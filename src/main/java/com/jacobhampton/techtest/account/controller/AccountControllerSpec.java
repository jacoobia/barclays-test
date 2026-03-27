package com.jacobhampton.techtest.account.controller;

import com.jacobhampton.techtest.account.dto.AccountResponseDto;
import com.jacobhampton.techtest.account.dto.CreateAccountRequestDto;
import com.jacobhampton.techtest.account.dto.UpdateAccountRequestDto;
import com.jacobhampton.techtest.shared.dto.ErrorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Accounts", description = "Account management endpoints")
public interface AccountControllerSpec {

    @Operation(summary = "Create a new account", description = "Creates a new bank account for the authenticated user. Requires authentication.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Account created successfully",
                    content = @Content(schema = @Schema(implementation = AccountResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized — missing or invalid access token",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    ResponseEntity<AccountResponseDto> createAccount(@Valid @RequestBody CreateAccountRequestDto body);

    @Operation(summary = "Get all accounts", description = "Retrieves all accounts for the authenticated user. Requires authentication.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Accounts retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AccountResponseDto.class)))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized — missing or invalid access token",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    ResponseEntity<List<AccountResponseDto>> getAccounts();

    @Operation(summary = "Get account by account number", description = "Retrieves a single account by its account number. Requires authentication.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Account found",
                    content = @Content(schema = @Schema(implementation = AccountResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized — missing or invalid access token",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Account not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    ResponseEntity<AccountResponseDto> getAccount(@Valid @NotNull @Parameter(description = "Account number") @PathVariable String accountNumber);

    @Operation(summary = "Update an account", description = "Partially updates an existing account. Requires authentication.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Account updated successfully",
                    content = @Content(schema = @Schema(implementation = AccountResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized — missing or invalid access token",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Account not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    ResponseEntity<AccountResponseDto> updateAccount(@Valid @NotNull @Parameter(description = "Account number") @PathVariable String accountNumber,
                                                     @Valid @RequestBody UpdateAccountRequestDto body);

    @Operation(summary = "Delete an account", description = "Deletes an account by its account number. Requires authentication.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Account deleted successfully"),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized — missing or invalid access token",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Account not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    ResponseEntity<Void> deleteAccount(@Valid @NotNull @Parameter(description = "Account number") @PathVariable String accountNumber);

}
