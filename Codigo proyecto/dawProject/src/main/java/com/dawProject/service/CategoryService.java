package com.dawProject.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawProject.model.Category;
import com.dawProject.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}
	
	public Category findById(Integer id) {
		Category category = null;
		for (Category c: categoryRepository.findAll()) {
			if (c.getCategorieId()==id) {
				category = c;
			}
		}
		return category;
	}	
	
	
	public Category saveCategory(Category category) {	
		boolean existe = false;
		for (Category c : categoryRepository.findAll()) {
			if(category.getCategorieName().equals(c.getCategorieName())) {
				existe = true;
				break;
			}
		}
		
		if (!existe) {
			categoryRepository.save(category);
		}
		return category;
	}
	
	public Category delete(Category category) {
		for (Category c : categoryRepository.findAll()) {
			if(category.getCategorieId()==c.getCategorieId())
				categoryRepository.delete(c);
		}
		return category;
	}

}
