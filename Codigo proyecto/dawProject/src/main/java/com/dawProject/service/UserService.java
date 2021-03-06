package com.dawProject.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.dawProject.model.User;
import com.dawProject.repository.UserRepository;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public User findByusername(String username) {
		User user = null;
		for (User u: userRepository.findAll()) {
			if (u.getUsername().equals(username)) {
				user = u;
			}
		}
		return user;
	}	
	
	public boolean findByusernameExt(String username) {
		boolean existe = false;
		for (User u: userRepository.findAll()) {
			if (u.getUsername().equals(username)) {
				existe = true;
			}
		}
		return existe;
	}
	
	public User save(User user) {	
		String password_plaintext = user.getPassword();
		user.setPassword(hashPassword(password_plaintext));
		user.setRole("user");
		userRepository.save(user);
		return user;
	}
	
	public static String hashPassword(String password_plaintext) {


		return(BCrypt.hashpw(password_plaintext, BCrypt.gensalt(12)));
	}
	
	public User delete(User user) {
		for (User u : userRepository.findAll()) {
			if(user.getUsername().equals(u.getUsername()))
				userRepository.delete(u);
		}
		return user;
	}
	

	
	public User login(User user) {
		 ModelMap model = null;
		 User find = null;
		for (User u : userRepository.findAll()) {
			if(user.getUsername().equals(u.getUsername())) {
				if(checkPassword(user.getPassword(),u.getPassword())) {
					find = u;
					String fecha = LocalDate.now().toString();
					System.out.println(fecha);
					System.out.println("Login");
				}
			}
		}
		return find;
	} 
	
	public User loginString(User user, String password) {
		 ModelMap model = null;
		 User find = null;
		for (User u : userRepository.findAll()) {
			if(user.getUsername().equals(u.getUsername())) {
				if(checkPassword(password,u.getPassword())) {
					find = u;
					String fecha = LocalDate.now().toString();
					System.out.println(fecha);
					System.out.println("Login");
				}
			}
		}
		return find;
	} 
	
	public static boolean checkPassword(String password_plaintext, String stored_hash) {
		boolean password_verified = false;

		if(null == stored_hash || !stored_hash.startsWith("$2a$"))
			throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

		password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

		return(password_verified);
	}
	
	public User update(User user) {
		String password_plaintext = user.getPassword();
		user.setPassword(hashPassword(password_plaintext));
		for (User u : userRepository.findAll()) {
			if(user.getUsername().equals(u.getUsername()))
				u.setPassword(user.getPassword());
				userRepository.save(u);
		}
		return user;
	}

	
}
