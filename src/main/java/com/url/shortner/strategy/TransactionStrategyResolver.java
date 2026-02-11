package com.url.shortner.strategy;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.url.shortner.dto.TransactionType;
import com.url.shortner.exception.BusinessException;
import com.url.shortner.exception.ErrorCodes;

@Component
public class TransactionStrategyResolver {

    private final Map<TransactionType, TransactionStrategy> strategies =
            new EnumMap<>(TransactionType.class);

    public TransactionStrategyResolver(
            CreditTransactionStrategy creditStrategy,
            DebitTransactionStrategy debitStrategy) {

        strategies.put(TransactionType.CREDIT, creditStrategy);
        strategies.put(TransactionType.DEBIT, debitStrategy);
    }

    public TransactionStrategy resolve(TransactionType transactionType) {
        TransactionStrategy strategy = strategies.get(transactionType);

        if (strategy == null) {
            throw new BusinessException(
                    "Unsupported transaction type: " + transactionType,
                    ErrorCodes.BUSINESS_ERROR
            );
        }

        return strategy;
    }
}
