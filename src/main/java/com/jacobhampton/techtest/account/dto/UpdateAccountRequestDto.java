package com.jacobhampton.techtest.account.dto;

import com.jacobhampton.techtest.account.model.AccountType;
import com.jacobhampton.techtest.shared.validation.AtLeastOneField;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@AtLeastOneField(message = "At least one field (name or accountType) must be provided")
public record UpdateAccountRequestDto(
        @Size(max = 15, message = "Names must be at most 10 characters")
        String name,
        AccountType accountType
) { }
