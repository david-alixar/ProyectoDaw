package com.dawProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawProject.model.Customer;
import com.dawProject.model.User;
import com.dawProject.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}
	
	public Customer findByusername(String username) {
		Customer customer = null;
		for (Customer c: customerRepository.findAll()) {
			if (c.getUsername().equals(username)) {
				customer = c;
			}
		}
		return customer;
	}	
	
	public List<Customer> findByPostalcode() {
		return customerRepository.findByPostalcode("41012");
	}
	
	public Customer save(Customer customer) {	
		customerRepository.save(customer);
		return customer;
	}
	
	public Customer delete(Customer customer) {
		for (Customer c : customerRepository.findAll()) {
			if(customer.getUsername().equals(c.getUsername()))
				customerRepository.delete(c);
		}
		return customer;
	}

}
