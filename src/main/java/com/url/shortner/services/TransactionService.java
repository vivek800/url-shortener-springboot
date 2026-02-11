package com.url.shortner.services;

import java.math.BigDecimal;
import java.util.List;

import com.url.shortner.dto.TransactionAccountView;
import com.url.shortner.dto.TransactionType;
import com.url.shortner.entity.Transaction;

public interface TransactionService {

   Transaction	createTransaction(Long accountId,
           BigDecimal amount,
           TransactionType txnType);
   
    
   List<TransactionAccountView> getSuccessfulTransactions(Long accountId);
}
