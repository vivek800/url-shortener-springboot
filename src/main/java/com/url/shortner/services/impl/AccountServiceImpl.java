package com.url.shortner.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.url.shortner.dto.AccountStatus;
import com.url.shortner.dto.AccountType;
import com.url.shortner.entity.Account;
import com.url.shortner.entity.Customer;
import com.url.shortner.exception.BusinessException;
import com.url.shortner.exception.ErrorCodes;
import com.url.shortner.records.CreateAccountRequest;
import com.url.shortner.repository.AccountRepository;
import com.url.shortner.repository.CustomerRepository;
import com.url.shortner.services.AccountService;

import jakarta.transaction.Transactional;


 @Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountServiceImpl(AccountRepository accountRepository,
                              CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Account createAccount(CreateAccountRequest request) {

        if (accountRepository.existsByCustomerCustomerIdAndAccountType(
                request.customerId(),
                request.accountType())) {

            throw new BusinessException(
                    "Customer already has a " + request.accountType() + " account",
                    ErrorCodes.BUSINESS_ERROR
            );
        }

        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new BusinessException(
                        "Customer not found with id " + request.customerId(),
                        ErrorCodes.BUSINESS_ERROR
                ));

        Account account = new Account();
        account.setCustomer(customer);
        account.setAccountType(request.accountType());
        account.setBalance(request.initialBalance());
        account.setOpenedDate(LocalDate.now());
        account.setStatus(AccountStatus.ACTIVE);

        try {
            return accountRepository.save(account);
        } catch (DataIntegrityViolationException ex) {
            throw new BusinessException(
                    "Customer already has a " + request.accountType() + "account",
                    ErrorCodes.BUSINESS_ERROR
            );
        }
    }

	@Override
	public List<Account> getAllAccount() {
		
		return accountRepository.findAll();
	}
}
