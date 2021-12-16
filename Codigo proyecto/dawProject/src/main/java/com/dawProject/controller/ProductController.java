package com.dawProject.controller;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.dawProject.form.AltaProductoForm;
import com.dawProject.model.*;
import com.dawProject.service.*;



@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private OrderService orderService;
	
	
	@GetMapping("/listProducts")
	public String home(Model model, HttpServletRequest request){
		User user = obtenerUsuarioSesion(request);
		model.addAttribute("user", user);
		model.addAttribute("products", productService.findAll());
		return "/productsList";	
	}
	
	@GetMapping("/newProduct")
	public String register(Model model, HttpServletRequest request) {
		User user = obtenerUsuarioSesion(request);
		if (user==null) {
			model.addAttribute("user", new User());
			return "/user/login";
		}
		else if (user.getRole().equals("admin")) {
			model.addAttribute("altaProductoForm", new AltaProductoForm());
			model.addAttribute("categories", categoryService.findAll());
			return "/admin/newProduct";
		} else {
			model.addAttribute("message", "No permitido");
			return "/user/welcomeUser";
		}
	}
	
	
	@PostMapping("/saveProduct")
	public String save(@ModelAttribute("altaProductoForm") AltaProductoForm altaProductoForm, Model model) {
		Category category = categoryService.findById(altaProductoForm.getIdCategoria());
		Product product = new Product(altaProductoForm.getNombreProducto(),altaProductoForm.getDescripcion(),altaProductoForm.getPrecio(),altaProductoForm.getCantidad(),altaProductoForm.getImagen(),altaProductoForm.getMarca(),altaProductoForm.getModelo(),altaProductoForm.getYear(),category);
		productService.save(product);
		model.addAttribute("products", productService.findAll());
		model.addAttribute("message", "Producto añadido con éxito");
		return "/admin/welcomeAdmin";
	}
	
		@GetMapping("/deleteProduct")
	public String delete(Model model, HttpServletRequest request) {
		User user = obtenerUsuarioSesion(request);
		model.addAttribute("product", new Product());
		if (user==null) {
			return "/user/login";
		}
		else if (user.getRole().equals("admin")) {
			return "/admin/deleteproduct";
		} else {
			return "/user/welcomeUser";
		}	
	}

	@PostMapping("/ProductDeleted")
	public String deleted(@ModelAttribute("product") Product product, Model model) {
		Product product2 = productService.findByProductCode(product.getProductCode());
		productService.delete(product2);
		return "/user/welcomeAdmin";
	}
	
	@GetMapping("/productDetail/{productCode}")
	public String buyProduct(
			HttpServletRequest request,
			Model model,
			@PathVariable("productCode") int productCode) {
		Product product = productService.findByProductCode(productCode);
		Product product2 = new Product();
		User user = obtenerUsuarioSesion(request);
		model.addAttribute("user", user);
		model.addAttribute("product", product);
		model.addAttribute("productBuy", product2);
		return "/productDetailVist";
		
	}
	
	@GetMapping("/updateProduct/{productCode}")
	public String updateProduct(
			Model model,
			@PathVariable("productCode") int productCode) {
		Product product = productService.findByProductCode(productCode);
		//product.setProductCode(productCode);
		
		model.addAttribute("product", product);
		return "/admin/updateProduct";
		
	}
	
	@PostMapping("/saveUpdateProduct")
	public String saveUpdateProduct(@ModelAttribute("product") Product product, Model model) {
		
		System.out.println(product.getPrice());
		System.out.println(product.getProductAvailable());
		System.out.println(product.getYear());

		product.setCategory(categoryService.findById(product.getCategory().getCategorieId()));
		productService.save(product);
		model.addAttribute("message", "Hecho! Producto actualizado");
		return "/admin/welcomeAdmin";
	}
	
	@GetMapping("/productListCategory/{category}")
	public String productListCategory(HttpServletRequest request,
			Model model,
			@PathVariable("category") int category) {
		User user = obtenerUsuarioSesion(request);
		model.addAttribute("user", user);
		
		model.addAttribute("message", "Hecho! Vista filtrada por categoría");
		model.addAttribute("products", productService.findAllCategory(category));
		return "/productsList";	
		
	}
	
	
	private User obtenerUsuarioSesion(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (User) session.getAttribute("user");
	}

}
