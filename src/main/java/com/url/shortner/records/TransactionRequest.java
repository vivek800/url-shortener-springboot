package com.url.shortner.records;

import java.math.BigDecimal;

public record TransactionRequest(
        Integer accountId,
        String txnType,  
        BigDecimal amount
) {}
