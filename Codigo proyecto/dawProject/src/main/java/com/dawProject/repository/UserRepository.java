package com.dawProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dawProject.model.User;

public interface UserRepository extends JpaRepository<User, String> {
	
}
