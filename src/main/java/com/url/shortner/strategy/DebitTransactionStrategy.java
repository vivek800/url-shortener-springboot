package com.url.shortner.strategy;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.url.shortner.entity.Account;
import com.url.shortner.exception.BusinessException;
import com.url.shortner.exception.ErrorCodes;

@Component
public class DebitTransactionStrategy implements TransactionStrategy {

    @Override
    public void apply(Account account, BigDecimal amount) {

        if (account.getBalance().compareTo(amount) < 0) {
            throw new BusinessException(
                    "Insufficient balance",
                    ErrorCodes.BUSINESS_ERROR
            );
        }

        account.setBalance(account.getBalance().subtract(amount));
    }
}
