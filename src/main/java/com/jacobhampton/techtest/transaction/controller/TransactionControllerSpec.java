package com.jacobhampton.techtest.transaction.controller;

import com.jacobhampton.techtest.shared.dto.ErrorResponseDto;
import com.jacobhampton.techtest.transaction.dto.CreateTransactionRequestDto;
import com.jacobhampton.techtest.transaction.dto.TransactionResponseDto;
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

@Tag(name = "Transactions", description = "Transaction management endpoints")
public interface TransactionControllerSpec {

    @Operation(summary = "Create a transaction", description = "Creates a deposit or withdrawal transaction against an account. Requires authentication.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Transaction created successfully",
                    content = @Content(schema = @Schema(implementation = TransactionResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error or insufficient funds",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized — missing or invalid access token",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden — account belongs to another user",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Account not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    ResponseEntity<TransactionResponseDto> createTransaction(
            @Valid @NotNull @Parameter(description = "Account number") @PathVariable String accountNumber,
            @Valid @RequestBody CreateTransactionRequestDto body
    );

    @Operation(summary = "Get all transactions", description = "Retrieves all transactions for a given account. Requires authentication.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Transactions retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TransactionResponseDto.class)))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized — missing or invalid access token",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden — account belongs to another user",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Account not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    ResponseEntity<List<TransactionResponseDto>> getTransactions(
            @Valid @NotNull @Parameter(description = "Account number") @PathVariable String accountNumber
    );

    @Operation(summary = "Get transaction by ID", description = "Retrieves a single transaction by its ID for a given account. Requires authentication.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Transaction found",
                    content = @Content(schema = @Schema(implementation = TransactionResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized — missing or invalid access token",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden — account belongs to another user",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Account or transaction not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    ResponseEntity<TransactionResponseDto> getTransaction(
            @Valid @NotNull @Parameter(description = "Account number") @PathVariable String accountNumber,
            @Valid @NotNull @Parameter(description = "Transaction ID") @PathVariable String transactionId
    );

}

