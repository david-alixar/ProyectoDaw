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
		//Customer customer = customerService.findByusername(user.getUsername());
		if(user.getRole().equals("admin"))
			return "/admin/list";
		else
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
	public String update(Model model) {
		model.addAttribute("update", new UpdateUsuarioForm());
		return "/user/update";
	}
	
	@PostMapping("/saveUpdate")
	public String updateUser(@ModelAttribute("update") UpdateUsuarioForm update, Model model, HttpServletRequest request) {
		HttpSession mysession = request.getSession(true);
		User user = obtenerUsuarioSesion(request);
		Customer customer = customerService.findByusername(user.getUsername());
		String actualizado = "";
		
		if (!update.getPasswordOld().equals("")) {
			User user2 = userService.loginString(user, update.getPasswordOld());
			if (user2 != null) {
				if (update.getPassword().equals(update.getPassword2())) {
					user.setPassword(update.getPassword());
					actualizado += "Contraseña actualizada con éxito. Por favor, inicie sesión de nuevo";
					mysession.setAttribute("user", null);
				} else {
					model.addAttribute("message", "Error! Las nuevas contraseñas no coinciden");
					return "/user/update";
				}
			}
			else {
				model.addAttribute("message", "Error! La contraseña antigua no coincide");
				return "/user/update";
			}
		}	
		
		if (!update.getAddress().equals("")) {
			customer.setAddress(update.getAddress());
			actualizado += "Dirección actualizada con éxito\n";
		}
		if (!update.getCity().equals("")) {
			customer.setCity(update.getCity());
			actualizado += "Ciudad actualizada con éxito\n";
		}
		if (!update.getCountry().equals("")) {
			customer.setCountry(update.getCountry());
			actualizado += "País actualizado con éxito\n";
		}
		if (!update.getEmail().equals("")) {
			customer.setEmail(update.getEmail());
			actualizado += "Email actualizado con éxito\n";
		}
		if (!update.getLastname().equals("")) {
			customer.setLastname(update.getLastname());
			actualizado += "Apellidos actualizados con éxito\n";
		}
		if (!update.getPhone().equals("")) {
			customer.setPhone(update.getPhone());
			actualizado += "Teléfono actualizado con éxito\n";
		}
		if (!update.getPostalcode().equals("")) {
			customer.setPostalcode(update.getPostalcode());
			actualizado += "Código postal actualizado con éxito\n";
		}
		if (!update.getState().equals("")) {
			customer.setState(update.getState());
			actualizado += "Estado actualizado con éxito\n";
		}
			
		userService.update(user);
		customerService.save(customer);
		model.addAttribute("message", actualizado);
		if(user.getRole().equals("admin")) {
			return "/admin/welcomeAdmin";
		} else  {
			return "/user/welcomeUser";
		}
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
		return "redirect:/login";
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