package com.url.shortner.records;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

import com.url.shortner.dto.AccountType;

public record CreateAccountRequest(

        @NotNull(message = "Customer id is required")
        @Positive(message = "Customer id must be positive")
        Integer customerId,

        @NotNull(message = "Account type is required")
        AccountType accountType,

        @NotNull(message = "Initial balance is required")
        @DecimalMin(value = "0.0", inclusive = true,
                message = "Initial balance must be zero or greater")
        BigDecimal initialBalance
) {}
