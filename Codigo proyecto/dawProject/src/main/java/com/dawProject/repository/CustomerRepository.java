package com.dawProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dawProject.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {

//	@Query("select o from Orders where o.customer = ?1")
//	public List<Orders> findByCustomer(String username);
	
	public List<Customer> findByPostalcode(String postalCode);
}
