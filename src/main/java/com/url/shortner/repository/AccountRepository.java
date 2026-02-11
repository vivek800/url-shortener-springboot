package com.url.shortner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.url.shortner.dto.AccountType;
import com.url.shortner.entity.Account;

public interface AccountRepository  extends JpaRepository<Account ,Long>{
   
	Optional<Account> findByAccountIdAndStatus(Long accountId, String status);
	
	
	 boolean existsByCustomerCustomerIdAndAccountType(
	            Integer customerId,
	            AccountType accountType
	    );
}
