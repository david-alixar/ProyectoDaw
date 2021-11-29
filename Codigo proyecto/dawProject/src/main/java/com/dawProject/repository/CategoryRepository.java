package com.dawProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dawProject.model.Category;
import com.dawProject.model.Customer;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	
}
