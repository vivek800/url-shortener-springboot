package com.url.shortner.strategy;

import java.math.BigDecimal;

import com.url.shortner.entity.Account;

public interface TransactionStrategy {
	 void apply(Account account, BigDecimal amount);
}
