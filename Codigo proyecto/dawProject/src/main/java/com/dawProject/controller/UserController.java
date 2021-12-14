package com.dawProject.controller;

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

import com.dawProject.form.*;
import com.dawProject.model.Customer;
import com.dawProject.model.Order;
import com.dawProject.model.User;
import com.dawProject.service.CustomerService;
import com.dawProject.service.UserService;



@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomerService customerService;
	
	
	@GetMapping("/listUsers")
	public String home(Model model, HttpServletRequest request){

		model.addAttribute("users", userService.findAll());
		model.addAttribute("customers", customerService.findAll());
		User user = obtenerUsuarioSesion(request);
		System.out.println(user);
		//Customer customer = customerService.findByusername(user.getUsername());
		if(user == null) {
			model.addAttribute("user", new User());
			model.addAttribute("message", "Error! Se requiere ser administrador");
			return "/user/login";	
		}
		else if(user.getRole().equals("admin"))
			return "/admin/list";
		else
			model.addAttribute("user", new User());
			model.addAttribute("message", "Error! Se requiere ser administrador");
			return "/user/login";	
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("altaUsuarioForm", new AltaUsuarioForm());
		return "/user/register";
	}
	
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "/user/login";
	}
	
	@GetMapping("/logout")
	public String logout(Model model, HttpServletRequest request) {
		//HttpSession mysession = request.getSession();
		HttpSession mysession = request.getSession(true);
		mysession.setAttribute("user", null);
		//mysession.invalidate();
		return "redirect:/login";
	}
	
	@GetMapping("/update")
	public String update(Model model, HttpServletRequest request) {
		HttpSession mysession = request.getSession(true);
		User user = obtenerUsuarioSesion(request);
		if(user == null) {
			model.addAttribute("user", new User());
			model.addAttribute("message", "Error! Se requiere estar autenticado");
			return "/user/login";	
		} else {
			Customer customer = customerService.findByusername(user.getUsername());
			model.addAttribute("customer", customer);
			model.addAttribute("update", new UpdateUsuarioForm());
			return "/user/update";
		}
	}
	
	@GetMapping("/updatePassword")
	public String updatePassword(Model model, HttpServletRequest request) {
		HttpSession mysession = request.getSession(true);
		User user = obtenerUsuarioSesion(request);
		if(user == null) {
			model.addAttribute("user", new User());
			model.addAttribute("message", "Error! Se requiere estar autenticado");
			return "/user/login";	
		} else {
			model.addAttribute("update", new UpdateUsuarioForm());
			return "/user/updatePassword";
		}
	}
	
	@PostMapping("/saveUpdate")
	public String updateUser(@ModelAttribute("update") UpdateUsuarioForm update, Model model, HttpServletRequest request) {
		HttpSession mysession = request.getSession(true);
		User user = obtenerUsuarioSesion(request);
		if(user == null) {
			model.addAttribute("user", new User());
			model.addAttribute("message", "Error! Se requiere estar autenticado");
			return "/user/login";	
		}
		Customer customer = new Customer(user.getUsername(), update.getName(), update.getLastname(), update.getEmail(), update.getAddress(), update.getPhone(), update.getCity(), update.getPostalcode(), update.getCountry(), update.getState());

		customerService.save(customer);
		model.addAttribute("message", "Hecho! Información del usuario actualizada correctamente");
		if(user.getRole().equals("admin")) {
			return "/admin/welcomeAdmin";
		} else  {
			return "/user/welcomeUser";
		}
	}
	
	@PostMapping("/saveUpdatePassword")
	public String saveUpdatePassword(@ModelAttribute("update") UpdateUsuarioForm update, Model model, HttpServletRequest request) {
		HttpSession mysession = request.getSession(true);
		User user = obtenerUsuarioSesion(request);
		if(user == null) {
			model.addAttribute("user", new User());
			model.addAttribute("message", "Error! Se requiere estar autenticado");
			return "/user/login";	
		}
		
		if (!update.getPasswordOld().equals("")) {
			User user2 = userService.loginString(user, update.getPasswordOld());
			if (user2 != null) {
				if (update.getPassword().equals(update.getPassword2())) {
					user.setPassword(update.getPassword());
				} else {
					model.addAttribute("message", "Error! Las nuevas contraseñas no coinciden");
					return "/user/updatePassword";
				}
			}
			else {
				model.addAttribute("message", "Error! La contraseña antigua no coincide");
				return "/user/updatePassword";
			}
		} userService.save(user);
		model.addAttribute("message", "Hecho! Contraseña actualizada correctamente. Por favor, inicie sesión de nuevo");
		model.addAttribute("user", new User());
		return "/user/login";
		
	}
	
	@GetMapping("/inicio")
	public String inicio(Model model, HttpServletRequest request) {
		User user = obtenerUsuarioSesion(request);
		if(user == null)
			return "redirect:/";
		else if(user.getRole().equals("admin")) {
			return "/admin/welcomeAdmin";
		} else  {
			return "/user/welcomeUser";
		}
	}
	
	@PostMapping("/checkLogin")
	public String checkLogin(@ModelAttribute("user") User user, Model model, HttpServletRequest request) {
		User user1 = userService.login(user);
		model.addAttribute("users", userService.findAll());
		HttpSession mysession = request.getSession(true);
		mysession.setAttribute("user", user1);
		User user2 = (User) mysession.getAttribute("user");
		if(user2 == null)
			return "/user/login";
		else if(user2.getRole().equals("admin")) {
			model.addAttribute("message", "Bienvenido, " + user.getUsername());
			return "/admin/welcomeAdmin";
		}
		else if(user2.getRole().equals("user")) {
			model.addAttribute("message", "Bienvenido, " + user.getUsername());
			return "/user/welcomeUser";
		}
		else
			model.addAttribute("user", new User());
			return "/user/login";
	}
	
	@PostMapping("/saveUser")
	public String save(@ModelAttribute("altaUsuarioForm") AltaUsuarioForm altaUsuarioForm, Model model) {
		Customer customer = new Customer(altaUsuarioForm.getUsername(),altaUsuarioForm.getName(),altaUsuarioForm.getLastname(),altaUsuarioForm.getEmail(),altaUsuarioForm.getAddress(),altaUsuarioForm.getPhone(),altaUsuarioForm.getCity(),altaUsuarioForm.getPostalcode(),altaUsuarioForm.getCountry(),altaUsuarioForm.getState());
		User user = new User(altaUsuarioForm.getUsername(),altaUsuarioForm.getPassword(),altaUsuarioForm.getRole(), customer);
		customerService.save(customer);
		userService.save(user);
		model.addAttribute("customers", customerService.findAll());
		model.addAttribute("users", userService.findAll());
		model.addAttribute("message", "Usuario creado correctamente");
		model.addAttribute("user", new User());
		return "/user/login";
	}
	
	@GetMapping("/delete/{username}")
	public String delete(
			Model model,
			@PathVariable("username") String username, HttpServletRequest request) {
		Customer customer = customerService.findByusername(username);
		User user = userService.findByusername(username);
		
		if (user == null) {
			return "/admin/list";
		}
		if(user.getRole().equals("admin")) {
			model.addAttribute("customers", customerService.findAll());
			model.addAttribute("message", "Error! El usuario " + user.getUsername() + " es administrador, y no puede ser eliminado");
			return "/admin/list";
		} else {
			userService.delete(user);
			//customerService.delete(customer);
			model.addAttribute("customers", customerService.findAll());
			model.addAttribute("message", "La cuenta del usuario " + user.getUsername() + " ha sido eliminada con éxito");
			return "/admin/list";	
		}
		
	}
	
	@GetMapping("/reset/{username}")
	public String reset(
			Model model,
			@PathVariable("username") String username, HttpServletRequest request) {
		User user = userService.findByusername(username);
		
		if (user == null) {
			return "/admin/list";
		}
		else {
			user.setPassword("123456");
			userService.save(user);
			model.addAttribute("customers", customerService.findAll());
			model.addAttribute("message", "La contraseña del usuario " + user.getUsername() + " ha sido reseteada con éxito");
			return "/admin/list";	
		}
		
	}
	
//		@GetMapping("/delete")
//	public String delete(Model model) {
//		model.addAttribute("user", new User());
//		return "/user/delete";
//	}
//	
//	@GetMapping("/deleteUser2/{niduser}")
//	public String delete2(
//			Model model,
//			@PathVariable("niduser") String niduser) {
//		userService.deleteByNid(Integer.parseInt(niduser));
//		return "redirect:/users";
//	}
//	
//	@PostMapping("/deleteUser")
//	public String deleted(@ModelAttribute("user") User user, Model model) {
//		Customer customer = customerService.findByusername(user.getUsername());
//		customerService.delete(customer);
//		userService.delete(user);
//		return "/user/login";
//	}
	
	private User obtenerUsuarioSesion(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (User) session.getAttribute("user");
	}

}