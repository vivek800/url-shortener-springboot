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
import com.url.shortner.entity.Customer;
import com.url.shortner.exception.BusinessException;
import com.url.shortner.records.CreateCustomerRequest;
import com.url.shortner.records.UrlResposne;
import com.url.shortner.services.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	private final CustomerService customerService;
	
	public CustomerController(CustomerService customerService)
	{
		this.customerService = customerService;
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<Customer>> createCustomer(
	        @RequestBody @Valid CreateCustomerRequest request) {

	    Customer customer = customerService.create(request);

	    return ResponseEntity.status(HttpStatus.CREATED)
	            .body(new ApiResponse<>(true, "Customer created successfully", customer));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<Customer>>> getAllCustomer() {

	    List<Customer> customers = customerService.getAllCustomers();

	    return ResponseEntity.ok(
	            new ApiResponse<>(true, "Customers fetched successfully", customers)
	    );
	}

	
	

	
}
