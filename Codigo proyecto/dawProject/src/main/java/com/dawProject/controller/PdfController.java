package com.dawProject.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dawProject.model.User;
import com.dawProject.service.PdfService;
import com.dawProject.service.ProductService;

@Controller
public class PdfController {
	
	@Autowired
	private PdfService pdfService;
	
	@GetMapping("/pdf")
	public String pdf (Model model, HttpServletRequest request) throws Exception{			
		
		pdfService.PDF();
		
		return "redirect:/";
	}
	
	
	
	private User obtenerUsuarioSesion(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (User) session.getAttribute("user");
	}

}
