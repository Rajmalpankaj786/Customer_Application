package com.springsecurity.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.springsecurity.exception.CustomerException;
import com.springsecurity.model.Customer;
import com.springsecurity.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerRepository customerRepository;
	

	@Override
	public Customer registerCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return customerRepository.save(customer);
	}

	@Override
	public Customer getCustomerDetailsByEmail(String email) throws CustomerException {
		// TODO Auto-generated method stub
		Customer customer = customerRepository.findByEmail(email).orElseThrow(() -> new CustomerException("Customer not found with this email"));
		return customer;
	}

	@Override
	public List<Customer> getAllCustomerDetails() throws CustomerException {
		// TODO Auto-generated method stub
		
		List<Customer> customers = customerRepository.findAll();
		
		if(customers.isEmpty()) {
			throw new CustomerException("No Customer Find");
		}
		
		return customers;
	}
	
	@Override
	public Page<Customer> getCustomers(String search, Pageable pageable) throws CustomerException {
        if (search != null && !search.isEmpty()) {
            return customerRepository.findByFirstNameContainingIgnoreCase(search, pageable);
        } else {
            return customerRepository.findAll(pageable);
        }
    }
	
	@Override
	public Customer updateCustomer(String email, Customer customerDetails) throws CustomerException {
		Customer customer = customerRepository.findByEmail(email).orElseThrow(() -> new CustomerException("Customer not found with this email"));
	            
	        customer.setFirstName(customerDetails.getFirstName());
	        customer.setLastName(customerDetails.getLastName());
	        customer.setAddress(customerDetails.getAddress());
	        customer.setCity(customerDetails.getCity());
	        customer.setStreet(customerDetails.getStreet());
	        customer.setState(customerDetails.getState());
	        customer.setPhone(customerDetails.getPhone());
	        return customerRepository.save(customer);
	    }
	
	
	@Override
	 public Customer deleteCustomer(String email)  throws CustomerException{
		 Customer customer = customerRepository.findByEmail(email).orElseThrow(() -> new CustomerException("Customer not found with this email"));
	        customerRepository.deleteById(customer.getUuid());
	        return customer;
	    }
	
	
	
}
