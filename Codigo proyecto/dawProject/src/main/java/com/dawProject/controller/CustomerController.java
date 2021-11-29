package com.dawProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.dawProject.model.Customer;
import com.dawProject.service.CustomerService;

@Controller
public class CustomerController {

	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/saveCustomer")
	public String save(@ModelAttribute("customer") Customer customer, Model model) {
		customerService.save(customer);
		model.addAttribute("customers", customerService.findAll());
		return "/user/login";
	}
}


	