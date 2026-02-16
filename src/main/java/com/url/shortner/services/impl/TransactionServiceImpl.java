package com.url.shortner.services.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.url.shortner.dto.EmailService;
import com.url.shortner.dto.TransactionAccountView;
import com.url.shortner.dto.TransactionResponse;
import com.url.shortner.dto.TransactionStatus;
import com.url.shortner.dto.TransactionType;
import com.url.shortner.entity.Account;
import com.url.shortner.entity.Transaction;
import com.url.shortner.exception.BusinessException;
import com.url.shortner.exception.ErrorCodes;
import com.url.shortner.mapper.TransactionMapper;
import com.url.shortner.repository.AccountRepository;
import com.url.shortner.repository.TransactionRepository;
import com.url.shortner.services.TransactionService;
import com.url.shortner.strategy.TransactionStrategy;
import com.url.shortner.strategy.TransactionStrategyResolver;

import org.springframework.transaction.annotation.Transactional;


 

@Service
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionStrategyResolver strategyResolver;
    private final EmailService emailService;
    public TransactionServiceImpl(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository,
            TransactionStrategyResolver strategyResolver,EmailService emailService) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.strategyResolver = strategyResolver;
        this.emailService = emailService;
    }

     @Override
     @Transactional
    public Transaction createTransaction(
            Long accountId,
            BigDecimal amount,
            TransactionType transactionType) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(
                    "Transaction amount must be greater than zero",
                    ErrorCodes.BUSINESS_ERROR
            );
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new BusinessException(
                        "Account not found with id " + accountId,
                        ErrorCodes.BUSINESS_ERROR
                ));

        TransactionStrategy strategy =
                strategyResolver.resolve(transactionType);

        strategy.apply(account, amount);  

        BigDecimal newBalance = account.getBalance();

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setTxnType(transactionType);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setTxnDate(LocalDateTime.now());
        transaction.setBalanceAfter(newBalance);

        accountRepository.save(account);
        Transaction savedTransaction = transactionRepository.save(transaction);

        try {
            emailService.sendTransactionEmail(
                    account.getCustomer().getEmail(),
                    account.getAccountId().toString(),
                    amount,
                    transactionType,
                    newBalance
            );
        } catch (Exception e) {
            System.err.println("Email sending failed: " + e.getMessage());
        }

        return savedTransaction;
    }


     @Override
     @Transactional(readOnly = true)
     public Page<TransactionAccountView> getSuccessfulTransactions(
             Long accountId, int page, int size) {

         Pageable pageable = PageRequest.of(
                 page,
                 size,
                 Sort.by(Sort.Direction.DESC, "txnDate")
         );

         return transactionRepository
                 .findSuccessfulTxnsWithAccount(accountId, pageable);
     }

	 @Override
	 public BigDecimal getTotalAmount(Long id, TransactionType type) {
		 
		return transactionRepository.getTotalAmountByType(id, type);
	 }


   

}
