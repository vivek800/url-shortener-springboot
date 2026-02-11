package com.url.shortner.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDetailsResponse(
        Long transactionId,
        Long accountId,
        TransactionType transactionType,
        BigDecimal amount,
        TransactionStatus status,
        LocalDateTime transactionDate,
        BigDecimal balanceAfter,
        String holderName,
        String customerEmail
) {}
