package com.dawProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dawProject.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	
}
