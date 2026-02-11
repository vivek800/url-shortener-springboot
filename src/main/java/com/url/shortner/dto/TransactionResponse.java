package com.url.shortner.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
		 Long transactionId,
         Long accountId,
         TransactionType transactionType,
         BigDecimal amount,
         TransactionStatus status,
         LocalDateTime transactionDate,
         BigDecimal balanceAfter
) {}
