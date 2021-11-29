package com.dawProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dawProject.model.Product;
import com.dawProject.model.User;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
}
