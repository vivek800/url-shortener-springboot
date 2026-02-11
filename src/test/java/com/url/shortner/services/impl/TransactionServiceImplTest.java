package com.url.shortner.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.url.shortner.dto.TransactionStatus;
import com.url.shortner.dto.TransactionType;
import com.url.shortner.entity.Account;
import com.url.shortner.entity.Transaction;
import com.url.shortner.exception.BusinessException;
import com.url.shortner.repository.AccountRepository;
import com.url.shortner.repository.TransactionRepository;
import com.url.shortner.strategy.TransactionStrategy;
import com.url.shortner.strategy.TransactionStrategyResolver;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionStrategyResolver strategyResolver;

    @Mock
    private TransactionStrategy transactionStrategy;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setAccountId(1L);
        account.setBalance(new BigDecimal("100000.00"));
    }

    @Test
    void createTransaction_success_debit() {
        // given
        BigDecimal amount = new BigDecimal("500.00");

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(strategyResolver.resolve(TransactionType.DEBIT))
                .thenReturn(transactionStrategy);

         
        doAnswer(invocation -> {
            Account acc = invocation.getArgument(0);
            BigDecimal amt = invocation.getArgument(1);
            acc.setBalance(acc.getBalance().subtract(amt));
            return null;
        }).when(transactionStrategy).apply(any(Account.class), any(BigDecimal.class));

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

         
        Transaction result = transactionService.createTransaction(
                1L, amount, TransactionType.DEBIT);

         
        assertNotNull(result);
        assertEquals(TransactionStatus.SUCCESS, result.getStatus());
        assertEquals(amount, result.getAmount());
        assertEquals(new BigDecimal("99500.00"), result.getBalanceAfter());
        assertEquals(new BigDecimal("99500.00"), account.getBalance());

        verify(accountRepository).save(account);
        verify(transactionRepository).save(any(Transaction.class));
        verify(transactionStrategy).apply(account, amount);
    }

    @Test
    void createTransaction_invalidAmount_shouldThrowException() {
         
        assertThrows(BusinessException.class, () ->
                transactionService.createTransaction(
                        1L, BigDecimal.ZERO, TransactionType.DEBIT));
    }

    @Test
    void createTransaction_accountNotFound_shouldThrowException() {
        when(accountRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () ->
                transactionService.createTransaction(
                        1L, new BigDecimal("100"), TransactionType.DEBIT));
    }
}
