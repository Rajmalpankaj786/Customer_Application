package com.springsecurity.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springsecurity.exception.CustomerException;
import com.springsecurity.model.Customer;

public interface CustomerService {

	public Customer registerCustomer(Customer customer);

	public Customer getCustomerDetailsByEmail(String email) throws CustomerException;

	public List<Customer> getAllCustomerDetails() throws CustomerException;

	public Page<Customer> getCustomers(String search, Pageable pageable) throws CustomerException;

	public Customer updateCustomer(String email, Customer customerDetails) throws CustomerException;
	
	public Customer deleteCustomer(String email)  throws CustomerException;

}
