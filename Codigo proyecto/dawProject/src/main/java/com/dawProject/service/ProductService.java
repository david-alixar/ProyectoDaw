package com.dawProject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawProject.model.OrderDetail;
import com.dawProject.model.Product;
import com.dawProject.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> findAll() {
		return productRepository.findAll();
	}
	
	public List<Product> findAllCategory(Integer categoryId) {
		List<Product> list = new ArrayList<>();
		for (Product p: productRepository.findAll()) {
			if (p.getCategory().getCategorieId()== categoryId) {
				list.add(p);
			}
		}
		return list;
	}
	
	public Product findByProductCode(Integer productCode) {
		Product product = null;
		for (Product p: productRepository.findAll()) {
			if (p.getProductCode()==productCode) {
				product = p;
			}
		}
		return product;
	}	
	
	public Product save(Product product) {	
		productRepository.save(product);
		return product;
	}
	
	public Product delete(Product product) {
		for (Product p : productRepository.findAll()) {
			if(product.getProductCode()==p.getProductCode())
				productRepository.delete(p);
		}
		return product;
	}

}
