package com.url.shortner.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.url.shortner.entity.Account;
import com.url.shortner.entity.Customer;
import com.url.shortner.records.CreateAccountRequest;
import com.url.shortner.repository.AccountRepository;
import com.url.shortner.repository.CustomerRepository;

public interface AccountService {

   Account createAccount(CreateAccountRequest a);

   List<Account> getAllAccount();
}
