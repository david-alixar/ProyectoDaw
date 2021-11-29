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
public class OrderController {

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
	
	@GetMapping("/cart")
	public String cart(Model model, HttpServletRequest request) {
		HttpSession mysession = request.getSession(true);
		User user = obtenerUsuarioSesion(request);
		if (user == null) {
			return "redirect:/login";
		}
		Customer customer = customerService.findByusername(user.getUsername());
		Order order = orderService.findByCustomerPending(customer.getUsername());
		if (order == null) {
			model.addAttribute("orderDetails", null);
		}
		else {
			model.addAttribute("orderDetails", orderDetailService.findCart(order.getOrderNumber()));
		}
		return "/user/cart";	
	}
	
	@GetMapping("/orders")
	public String orders(
		Model model, HttpServletRequest request) {
		User user = obtenerUsuarioSesion(request);
		if (user == null) {
			return "redirect:/login";
		}
		else if (user.getRole().equals("admin")) {
			model.addAttribute("orders", orderService.findAll());
		}
		else {
			model.addAttribute("orders", orderService.findAllByCustomer(user.getUsername()));
		}
		model.addAttribute("user", user);
		return "/orders";
	}
	
	@GetMapping("/buyProduct/{productCode}")
	public String buyProduct(
			Model model,
			@PathVariable("productCode") int productCode, HttpServletRequest request) {
		User user = obtenerUsuarioSesion(request);
		if (user == null) {
			return "redirect:/login";
		}
		Customer customer = customerService.findByusername(user.getUsername());
		
		Order order = orderService.findByCustomerPending(customer.getUsername());
		
		if (order == null) {
			order = new Order(LocalDate.now().toString(),"No finalizado", "Vacío", 0, customer);
			orderService.save(order);
		}
		
		Product product = productService.findByProductCode(productCode);
		OrderDetail orderDetail = orderDetailService.findByOrderDetailIdProduct(productCode, order.getOrderNumber());
		int productAvailable = product.getProductAvailable();
		
		if (productAvailable > 0) { 
		
			if(orderDetail == null) {
				orderDetail = new OrderDetail(1, product.getPrice(), product, order);
			} else {
				orderDetail.setQuantity(orderDetail.getQuantity() + 1);
				orderDetail.setSubtotal(orderDetail.getSubtotal() + product.getPrice());
			}
		} else {
			model.addAttribute("message", "Error! Cantidad insuficiente para el producto seleccionado");
			model.addAttribute("products", productService.findAll());
			return "/productsList";
		}
		product.setProductAvailable(productAvailable -1);
		productService.save(product);
		order.setTotal(order.getTotal() + product.getPrice());
		orderService.save(order);
		orderDetailService.save(orderDetail);
		model.addAttribute("orderDetails", orderDetailService.findCart(order.getOrderNumber()));
		model.addAttribute("message", "Producto añadido al carrito con éxito");
		return "/user/cart";
		
	}
	
	@GetMapping("/dropCart")
	public String dropCart(
		Model model, HttpServletRequest request) {
		User user = obtenerUsuarioSesion(request);
		if (user == null) {
			return "redirect:/login";
		}
		Customer customer = customerService.findByusername(user.getUsername());
		Order order = orderService.findByCustomerPending(customer.getUsername());
		List <OrderDetail> list = orderDetailService.findCart(order.getOrderNumber());
		Product product = null;
		
		for (OrderDetail o : list) {
			product = o.getProduct();
			product.setProductAvailable(o.getQuantity() + product.getProductAvailable());
			productService.save(product);
			orderDetailService.delete(o);
		}
		
		orderService.delete(order);
		model.addAttribute("products", productService.findAll());
		model.addAttribute("message", "Carrito vaciado con éxito");
		return "/productsList";
	}
	
	@GetMapping("/finalizeOrder")
	public String finalizeOrder(
		Model model, HttpServletRequest request) {
		User user = obtenerUsuarioSesion(request);
		if (user == null) {
			return "redirect:/login";
		}
		Customer customer = customerService.findByusername(user.getUsername());
		Order order = orderService.findByCustomerPending(customer.getUsername());
		
		orderService.changeStatus("Finalizado", order);
		model.addAttribute("orders", orderService.findAllByCustomer(user.getUsername()));
	
		return "/orders";
	}
	
	@GetMapping("/markPaid/{OrderNumber}")
	public String markPaid(
			Model model,
			@PathVariable("OrderNumber") int orderNumber, HttpServletRequest request) {
		User user = obtenerUsuarioSesion(request);
		if (user == null) {
			return "redirect:/login";
		}
		Order order = orderService.findByOrderNumber(orderNumber);
		
		orderService.changeStatus("Pagado", order);
		model.addAttribute("orders", orderService.findAllByCustomer(user.getUsername()));
		
		return "/orders";
		
	}
	
	@GetMapping("/detailOrder/{OrderNumber}")
	public String detailOrder(
			Model model,
			@PathVariable("OrderNumber") int orderNumber, HttpServletRequest request) {
		User user = obtenerUsuarioSesion(request);
		if (user == null) {
			return "redirect:/login";
		}
		Order order = orderService.findByOrderNumber(orderNumber);
		model.addAttribute("orderDetails", orderDetailService.findCart(orderNumber));
		return "/user/orderDetail";
		
	}
	
	
	
	private User obtenerUsuarioSesion(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (User) session.getAttribute("user");
	}

}
