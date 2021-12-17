package com.dawProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dawProject.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
}
