package com.url.shortner.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.url.shortner.dto.ApiResponse;
import com.url.shortner.entity.Account;
import com.url.shortner.records.CreateAccountRequest;
import com.url.shortner.services.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	
	private final AccountService accountService;
	
	public AccountController(AccountService accountService)
	{
		this.accountService = accountService;
	}
	
	
	 
	@PostMapping
	public ResponseEntity<ApiResponse<Account>> createAccount(
	        @RequestBody @Valid CreateAccountRequest request) {

	    Account account = accountService.createAccount(request);

	    return ResponseEntity.status(HttpStatus.CREATED)
	            .body(new ApiResponse<>(true, "Account created successfully", account));
	}
	
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<Account>>> getAllAccount() {

	    List<Account> accounts = accountService.getAllAccount();

	    return ResponseEntity.ok(
	            new ApiResponse<>(
	                    true,
	                    "Accounts fetched successfully",
	                    accounts
	            )
	    );
	}

	
	
}
