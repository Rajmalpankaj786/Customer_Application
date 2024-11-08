package com.springsecurity.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.springsecurity.model.Customer;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID>, PagingAndSortingRepository<Customer, UUID> {
    // Custom search method if needed (example below)
    Page<Customer> findByFirstNameContainingIgnoreCase(String firstName, Pageable pageable);

	
	
	public Optional<Customer> findByEmail(String email);
	
	
	
}
