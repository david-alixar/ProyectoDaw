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
import com.dawProject.form.UpdateUsuarioForm;
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
			model.addAttribute("user", new User());
			return "/user/login";
		}
		Customer customer = customerService.findByusername(user.getUsername());
		Order order = orderService.findByCustomerPending(customer.getUsername());
		if (order == null) {
			model.addAttribute("orderDetails", null);
		}
		else {
			model.addAttribute("orderDetails", orderDetailService.findCart(order.getOrderNumber()));
			model.addAttribute("total", order.getTotal());
		}
		return "/user/cart";	
	}
	
	@GetMapping("/orders")
	public String orders(
		Model model, HttpServletRequest request) {
		User user = obtenerUsuarioSesion(request);
		if (user == null) {
			model.addAttribute("user", new User());
			return "/user/login";
		}
		else if (user.getRole().equals("admin")) {
			model.addAttribute("orders", orderService.findAll());
			model.addAttribute("dates", orderService.findAllByDates(orderService.findAll()));
		}
		else {
			model.addAttribute("dates", orderService.findAllByDates(orderService.findAllByCustomer(user.getUsername())));
			model.addAttribute("orders", orderService.findAllByCustomer(user.getUsername()));
		}
		model.addAttribute("customers", orderService.findAllByUsername());
		model.addAttribute("user", user);
		model.addAttribute("order", new Order());
		return "/orders";
	}
	
	@PostMapping("/filterStatus")
	public String filterStatus(
		Model model, HttpServletRequest request, @ModelAttribute("order") Order order) {
		User user = obtenerUsuarioSesion(request);
		if (user == null) {
			model.addAttribute("user", new User());
			return "/user/login";
		}
		else if (user.getRole().equals("admin")) {
			model.addAttribute("orders", orderService.findAllByStatus(order.getStatus(),orderService.findAll()));
			model.addAttribute("dates", orderService.findAllByDates(orderService.findAll()));
		}
		else {
			model.addAttribute("orders", orderService.findAllByStatus(order.getStatus(), orderService.findAllByCustomer(user.getUsername())));
			model.addAttribute("dates", orderService.findAllByDates(orderService.findAllByCustomer(user.getUsername())));
		}
		model.addAttribute("user", user);
		model.addAttribute("message", "Pedidos filtrados por estado " + order.getStatus());
		model.addAttribute("customers", orderService.findAllByUsername());
		model.addAttribute("order", new Order());
		return "/orders";
	}
	
	@PostMapping("/filterUsername")
	public String filterUsername(
		Model model, HttpServletRequest request, @ModelAttribute("order") Order order) {
		User user = obtenerUsuarioSesion(request);
		if (user == null) {
			model.addAttribute("user", new User());
			return "/user/login";
		}
		else if (user.getRole().equals("user")) {
			model.addAttribute("orders", orderService.findAllByCustomer(user.getUsername()));
			model.addAttribute("dates", orderService.findAllByDates(orderService.findAllByCustomer(user.getUsername())));
			model.addAttribute("customers", orderService.findAllByUsername());
			model.addAttribute("user", user);
			model.addAttribute("order", new Order());
			return "/orders";
		}
		
		model.addAttribute("orders", orderService.findAllByCustomer(order.getCustomer().getUsername()));
		model.addAttribute("message", "Pedidos filtrados por usuario " + order.getCustomer().getUsername());
		model.addAttribute("dates", orderService.findAllByDates(orderService.findAll()));
		model.addAttribute("customers", orderService.findAllByUsername());
		model.addAttribute("user", user);
		model.addAttribute("order", new Order());
		return "/orders";
	}
	
	@PostMapping("/filterDate")
	public String filterDate(
		Model model, HttpServletRequest request, @ModelAttribute("order") Order order) {
		User user = obtenerUsuarioSesion(request);
		if (user == null) {
			model.addAttribute("user", new User());
			return "/user/login";
		}
		else if (user.getRole().equals("user")) {
			model.addAttribute("orders", orderService.findAllByDate(order.getDate(),orderService.findAllByCustomer(user.getUsername())));
			model.addAttribute("dates", orderService.findAllByDates(orderService.findAllByCustomer(user.getUsername())));
			model.addAttribute("customers", orderService.findAllByUsername());
			model.addAttribute("user", user);
			model.addAttribute("message", "Pedidos filtrados por fecha " + order.getDate());
			model.addAttribute("order", new Order());
			return "/orders";
		}
		
		model.addAttribute("orders",  orderService.findAllByDate(order.getDate(),orderService.findAll()));
		model.addAttribute("dates", orderService.findAllByDates(orderService.findAll()));
		model.addAttribute("message", "Pedidos filtrados por fecha " + order.getDate());
		model.addAttribute("customers", orderService.findAllByUsername());
		model.addAttribute("user", user);
		model.addAttribute("order", new Order());
		return "/orders";
	}
	
	@GetMapping("/manageOrder/{orderNumber}")
	public String manageOrder(
			Model model,
			@PathVariable("orderNumber") int orderNumber) {
		Order order = orderService.findByOrderNumber(orderNumber);
		
		model.addAttribute("order", order);
		return "/admin/manageOrder";
		
	}
	
	@PostMapping("/manageOrderVist")
	public String manageOrderVist(@ModelAttribute("product") Order order, Model model) {
		order.setCustomer(customerService.findByusername(order.getCustomer().getUsername()));
		orderService.save(order);
		model.addAttribute("message", "Hecho! Pedido actualizado");
		return "/admin/welcomeAdmin";
	}
	
	@GetMapping("/buyProduct/{productCode}")
	public String buyProduct(
			Model model,
			@PathVariable("productCode") int productCode, HttpServletRequest request) {
		User user = obtenerUsuarioSesion(request);
		if (user == null) {
			model.addAttribute("message", "Por favor, inicie sesión para realizar una compra");
			model.addAttribute("user", new User());
			return "/user/login";
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
		model.addAttribute("total", order.getTotal());
		return "/user/cart";
		
	}
	
	@PostMapping("/buyProductVist")
	public String buyProductVist(@ModelAttribute("productBuy") Product productBuy, Model model, HttpServletRequest request) {
		User user = obtenerUsuarioSesion(request);
		if (user == null) {
			model.addAttribute("message", "Por favor, inicie sesión para realizar una compra");
			model.addAttribute("user", new User());
			return "/user/login";
		}
		Customer customer = customerService.findByusername(user.getUsername());
		
		Order order = orderService.findByCustomerPending(customer.getUsername());
		
		if (order == null) {
			order = new Order(LocalDate.now().toString(),"No finalizado", "Vacío", 0, customer);
			orderService.save(order);
		}
		
		Product product = productService.findByProductCode(productBuy.getProductCode());
		OrderDetail orderDetail = orderDetailService.findByOrderDetailIdProduct(productBuy.getProductCode(), order.getOrderNumber());
		int productAvailable = product.getProductAvailable();
		float price = product.getPrice() * productBuy.getProductAvailable();
		
		if (productAvailable >= productBuy.getProductAvailable()) { 
		
			if(orderDetail == null) {
				orderDetail = new OrderDetail(productBuy.getProductAvailable(), price, product, order);
			} else {
				orderDetail.setQuantity(orderDetail.getQuantity() + productBuy.getProductAvailable());
				orderDetail.setSubtotal(orderDetail.getSubtotal() + price);
			}
		} else {
			model.addAttribute("message", "Error! Cantidad insuficiente para el producto seleccionado");
			model.addAttribute("products", productService.findAll());
			return "/productsList";
		}
		product.setProductAvailable(productAvailable - productBuy.getProductAvailable());
		productService.save(product);
		order.setTotal(order.getTotal() + price);
		orderService.save(order);
		orderDetailService.save(orderDetail);
		model.addAttribute("orderDetails", orderDetailService.findCart(order.getOrderNumber()));
		model.addAttribute("message", "Producto añadido al carrito con éxito");
		model.addAttribute("total", order.getTotal());
		return "/user/cart";
	}
	
	@GetMapping("/dropCart")
	public String dropCart(
		Model model, HttpServletRequest request) {
		User user = obtenerUsuarioSesion(request);
		if (user == null) {
			model.addAttribute("user", new User());
			model.addAttribute("message", "Es necesario iniciar sesión");
			return "/user/login";
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
			model.addAttribute("user", new User());
			model.addAttribute("message", "Es necesario iniciar sesión");
			return "/user/login";
		}
		if (user.getRole().equals("admin")) {
			model.addAttribute("dates", orderService.findAllByDates(orderService.findAll()));
		} else {
			model.addAttribute("dates", orderService.findAllByDates(orderService.findAllByCustomer(user.getUsername())));
		}
		Customer customer = customerService.findByusername(user.getUsername());
		Order order = orderService.findByCustomerPending(customer.getUsername());
		
		orderService.changeStatus("Finalizado", order);
		model.addAttribute("customers", orderService.findAllByUsername());
		model.addAttribute("message", "Información del pedido actualizada con éxito");
		model.addAttribute("orders", orderService.findAllByCustomer(user.getUsername()));
		model.addAttribute("order", new Order());
		model.addAttribute("user", user);
		return "/orders";
	}
	
	@GetMapping("/markPaid/{OrderNumber}")
	public String markPaid(
			Model model,
			@PathVariable("OrderNumber") int orderNumber, HttpServletRequest request) {
		User user = obtenerUsuarioSesion(request);
		if (user == null) {
			model.addAttribute("message", "Es necesario iniciar sesión");
			model.addAttribute("user", new User());
			return "/user/login";
		}
		
		if (user.getRole().equals("admin")) {
			model.addAttribute("dates", orderService.findAllByDates(orderService.findAll()));
		} else {
			model.addAttribute("dates", orderService.findAllByDates(orderService.findAllByCustomer(user.getUsername())));
		}
		Order order = orderService.findByOrderNumber(orderNumber);
		
		orderService.changeStatus("Pagado", order);
		model.addAttribute("message", "Información del pedido actualizada con éxito");
		model.addAttribute("orders", orderService.findAllByCustomer(user.getUsername()));
		model.addAttribute("user", user);
		model.addAttribute("order", new Order());
		return "/orders";
		
	}
	
	@GetMapping("/detailOrder/{OrderNumber}")
	public String detailOrder(
			Model model,
			@PathVariable("OrderNumber") int orderNumber, HttpServletRequest request) {
		User user = obtenerUsuarioSesion(request);
		if (user == null) {
			model.addAttribute("message", "Es necesario iniciar sesión");
			model.addAttribute("user", new User());
			return "/user/login";
		}
		Order order = orderService.findByOrderNumber(orderNumber);
		model.addAttribute("user", user);
		model.addAttribute("order", order);
		model.addAttribute("orderDetails", orderDetailService.findCart(orderNumber));
		return "/user/orderDetail";
		
	}
	
	
	
	private User obtenerUsuarioSesion(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (User) session.getAttribute("user");
	}

}
