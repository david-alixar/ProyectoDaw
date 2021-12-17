package com.dawProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dawProject.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	
}
