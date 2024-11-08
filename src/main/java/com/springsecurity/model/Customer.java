package com.springsecurity.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.Pattern;


@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID uuid;

	private String firstName;

	private String lastName;

	
	private String street;
	private String address;
	private String city;
	private String state;

	@Email(message = "Email should be valid")
	@Column(unique = true)
	private String email;

	@Pattern(regexp = "\\d{8,10}", message = "Phone number must be between 8 and 10 digits")
	private String phone;
	
	private String role;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	// Default constructor
	public Customer() {
	}

	// Parameterized constructor
	public Customer(String firstName, String lastName, String street, String address, String city, String state,
			String email, String phone, String role, String password) {
		this.uuid = UUID.randomUUID(); // Generates a unique UUID
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.address = address;
		this.city = city;
		this.state = state;
		this.email = email;
		this.phone = phone;
		this.role = role;
		this.password = password;
	}

	// Getters and Setters
	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString() {
		return "Customer{" + "uuid=" + uuid + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\''
				+ ", street='" + street + '\'' + ", address='" + address + '\'' + ", city='" + city + '\'' + ", state='"
				+ state + '\'' + ", email='" + email + '\'' + ", phone='" + phone + '\'' + '}';
	}

	
}
