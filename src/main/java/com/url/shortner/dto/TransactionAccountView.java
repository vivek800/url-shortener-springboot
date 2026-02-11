package com.url.shortner.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TransactionAccountView {

    Long getTxnId();
    BigDecimal getAmount();
    LocalDateTime getTxnDate();
    String getTxnType();
    String getStatus();
    BigDecimal getBalanceAfter();
    Long getAccountId();
    String getHolderName();
    String getCustomerEmail();
}
