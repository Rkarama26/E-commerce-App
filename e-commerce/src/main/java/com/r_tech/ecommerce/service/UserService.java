package com.r_tech.ecommerce.service;

import org.springframework.stereotype.Service;

import com.r_tech.ecommerce.exception.UserException;
import com.r_tech.ecommerce.model.User;

@Service
public interface UserService {

	public User findUserById(Long userId) throws UserException;
	
	public User findUserProfileByJwt(String jwt)throws UserException;
	
}
