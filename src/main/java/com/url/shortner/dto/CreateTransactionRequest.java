package com.url.shortner.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateTransactionRequest(

        @NotNull
        @Positive
        Long accountId,

        @NotNull
        @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
        BigDecimal amount,

        @NotNull
        TransactionType txnType
) {}

