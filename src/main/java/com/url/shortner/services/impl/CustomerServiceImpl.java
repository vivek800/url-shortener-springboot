package com.url.shortner.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.url.shortner.entity.Customer;
import com.url.shortner.exception.BusinessException;
import com.url.shortner.records.CreateCustomerRequest;
import com.url.shortner.repository.CustomerRepository;
import com.url.shortner.services.CustomerService;
 

@Service
public class CustomerServiceImpl implements CustomerService{

	
	private final CustomerRepository customerRepository;
	
	public CustomerServiceImpl(CustomerRepository customerRepository)
	{
		this.customerRepository = customerRepository;
	}
	
	@Override
	public Customer create(CreateCustomerRequest customerRequest) {

	    if (customerRepository.existsByEmail(customerRequest.email())) {
	    	throw new BusinessException("Customer with this email already exists");
	    }

	    Customer customer = new Customer();
	    customer.setName(customerRequest.name());
	    customer.setEmail(customerRequest.email());
	    customer.setCity(customerRequest.city());
	    customer.setCreatedAt(LocalDateTime.now());

	    return customerRepository.save(customer);
	}

	@Override
	public List<Customer> getAllCustomers() {
	    return customerRepository.findAll();
	}

}
