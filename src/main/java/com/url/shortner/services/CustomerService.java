package com.url.shortner.services;

import java.util.List;

import com.url.shortner.entity.Customer;
import com.url.shortner.exception.BusinessException;
import com.url.shortner.records.CreateCustomerRequest;

public interface CustomerService {

	     Customer create(CreateCustomerRequest customerRequest);

	    List<Customer> getAllCustomers();
	
}
