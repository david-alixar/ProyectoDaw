package com.dawProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dawProject.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
	
}
