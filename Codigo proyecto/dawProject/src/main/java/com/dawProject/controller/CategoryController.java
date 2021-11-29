package com.dawProject.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.dawProject.model.Category;
import com.dawProject.model.User;
import com.dawProject.service.CategoryService;

@Controller
public class CategoryController {

	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/listCategories")
	public String home(Model model){

		model.addAttribute("categories", categoryService.findAll());
			return "/admin/categoriesList";
	}
	
	@GetMapping("/newCategory")
	public String register(Model model, HttpServletRequest request) {
		model.addAttribute("category", new Category());
		User user = obtenerUsuarioSesion(request);
		if (user==null) {
			return "/user/login";
		}
		else if (user.getRole().equals("admin")) {
			return "/admin/newCategory";
		} else {
			return "/user/welcomeUser";
		}
	}
	
	@PostMapping("/saveCategory")
	public String save(@ModelAttribute("category") Category category, Model model) {
		model.addAttribute("customers", categoryService.findAll());
		categoryService.saveCategory(category);
		return "/admin/welcomeAdmin";
	}
	
	private User obtenerUsuarioSesion(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (User) session.getAttribute("user");
	}
}


	