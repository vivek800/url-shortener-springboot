package com.url.shortner.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;

import com.url.shortner.dto.TransactionAccountView;
import com.url.shortner.dto.TransactionType;
import com.url.shortner.entity.Transaction;

public interface TransactionService {

   Transaction	createTransaction(Long accountId,
           BigDecimal amount,
           TransactionType txnType);
   
    
   Page<TransactionAccountView> getSuccessfulTransactions(Long accountId ,int page, int size);
   
   BigDecimal getTotalAmount(Long id,TransactionType type);
}
