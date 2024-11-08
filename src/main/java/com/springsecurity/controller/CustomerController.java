package com.springsecurity.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.exception.CustomerException;
import com.springsecurity.model.Customer;
import com.springsecurity.service.CustomerService;

@RestController
@CrossOrigin("*")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// add this Customer with two authorities

	/*
	 * { "first_name": "Jane", "last_name": "Doe", "street": "Elvnu Street",
	 * "address": "H no 2 ", "city": "Delhi", "state": "Delhi", "email":
	 * "sam@gmail.com", "phone": "12345678", "role" : "user", "password" : "1234"
	 * 
	 * }
	 */

	
	// to register any customer...
	@PostMapping("/customers") // http://localhost:8081/customers
	public ResponseEntity<Customer> saveCustomerHandler(@RequestBody Customer customer) {

		customer.setPassword(passwordEncoder.encode(customer.getPassword()));

		customer.setRole("ROLE_" + customer.getRole().toUpperCase());

		Customer registeredCustomer = customerService.registerCustomer(customer);

		return new ResponseEntity<>(registeredCustomer, HttpStatus.ACCEPTED);

	}

	
	@GetMapping("/customers/{email}") // http://localhost:8081/customers/rajmal@gmail.com
	public ResponseEntity<Customer> getCustomerByEmailHandler(@PathVariable("email") String email)
			throws CustomerException {
		Customer customer = customerService.getCustomerDetailsByEmail(email);

		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

	@GetMapping("/customers") // http://localhost:8081/customers
	public ResponseEntity<List<Customer>> getAllCustomerHandler() throws CustomerException {
		List<Customer> customers = customerService.getAllCustomerDetails();
		return new ResponseEntity<>(customers, HttpStatus.OK);
	}

	@GetMapping("/signIn") // http://localhost:8084/signIn
	public ResponseEntity<String> getLoggedInCustomerDetailsHandler(Authentication auth) throws CustomerException {

		System.out.println(auth); // this Authentication object having Principle object details

		Customer customer = customerService.getCustomerDetailsByEmail(auth.getName());

		return new ResponseEntity<>(customer.getFirstName() + " " + customer.getLastName() + " Logged In Successfully",
				HttpStatus.OK);
	}

	@GetMapping("/getAllCustomersByfirstName")
	public ResponseEntity<Page<Customer>> getCustomers(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "firstName") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir, @RequestParam(required = false) String search)
			throws CustomerException {

		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return new ResponseEntity<Page<Customer>>(customerService.getCustomers(search, pageable), HttpStatus.OK);
	}
	
	
	@GetMapping("/getAllCustomersByEmail")
	public ResponseEntity<Page<Customer>> getCustomersByEmail(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "email") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir, @RequestParam(required = false) String search)
			throws CustomerException {

		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return new ResponseEntity<Page<Customer>>(customerService.getCustomers(search, pageable), HttpStatus.OK);
	}
	
	@GetMapping("/getAllCustomersByPhone")
	public ResponseEntity<Page<Customer>> getCustomersByPhone(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "phone") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir, @RequestParam(required = false) String search)
			throws CustomerException {

		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return new ResponseEntity<Page<Customer>>(customerService.getCustomers(search, pageable), HttpStatus.OK);
	}
	@GetMapping("/getAllCustomersBy/{sortBy}")
	public ResponseEntity<Page<Customer>> getCustomersByCity(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @PathVariable String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir, @RequestParam(required = false) String search)
			throws CustomerException {

		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return new ResponseEntity<Page<Customer>>(customerService.getCustomers(search, pageable), HttpStatus.OK);
	}

	@PutMapping("/customers/{email}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("email") String email,
			@RequestBody Customer customerDetails) throws CustomerException {
		Customer updatedCustomer = customerService.updateCustomer(email, customerDetails);
		return new ResponseEntity<Customer>(updatedCustomer, HttpStatus.OK);
	}

	@DeleteMapping("/customers/{email}")
	public ResponseEntity<Customer> deleteCustomer(@PathVariable String email) throws CustomerException {
		Customer customer = customerService.deleteCustomer(email);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
}
