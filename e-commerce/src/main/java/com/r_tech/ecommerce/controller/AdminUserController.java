package com.r_tech.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r_tech.ecommerce.exception.UserException;
import com.r_tech.ecommerce.model.User;
import com.r_tech.ecommerce.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin API's")
public class AdminUserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers(@RequestHeader("Authorization") String jwt) throws UserException {

		System.out.println("/api/users/profile");
		List<User> user = userService.findAllUsers();
		return new ResponseEntity<>(user, HttpStatus.ACCEPTED);

	}
}
