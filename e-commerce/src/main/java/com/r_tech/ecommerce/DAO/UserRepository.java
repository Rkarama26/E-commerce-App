package com.r_tech.ecommerce.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.r_tech.ecommerce.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	
	public User findByEmail(String email);

	public List<User> findAllByOrderByCreatedAtDesc();

}
