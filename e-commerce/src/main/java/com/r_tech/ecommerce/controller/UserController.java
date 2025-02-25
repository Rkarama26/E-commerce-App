package com.r_tech.ecommerce.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r_tech.ecommerce.exception.InvalidJwtTokenException;
import com.r_tech.ecommerce.exception.UserException;
import com.r_tech.ecommerce.model.User;
import com.r_tech.ecommerce.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/users")
@Tag(name = "User API's")
public class UserController {

	public UserController(UserService userService) {
		this.userService = userService;
	}

	private UserService userService;
	
	
	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException{
		
		System.out.println("getUserProfileHandler - called ");
		
		try {
			User user = userService.findUserProfileByJwt(jwt);
	        log.debug("Getting user profile for username: {}", user.getFirstName());

			return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
		} catch (InvalidJwtTokenException e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} 
	}

}
