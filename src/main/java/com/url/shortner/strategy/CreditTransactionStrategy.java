package com.url.shortner.strategy;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.url.shortner.entity.Account;

@Component
public class CreditTransactionStrategy  implements TransactionStrategy {

	@Override
    public void apply(Account account, BigDecimal amount) {
        account.setBalance(account.getBalance().add(amount));
    }

}
