package com.url.shortner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.url.shortner.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByCustomerId(Long customerId);

    boolean existsByEmail(String email);
}
