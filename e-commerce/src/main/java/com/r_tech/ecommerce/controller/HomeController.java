package com.r_tech.ecommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r_tech.ecommerce.response.ApiResponse;

@RestController
public class HomeController {

    @GetMapping("/")
	ResponseEntity<ApiResponse> homeController(){
		
		ApiResponse res = new ApiResponse("Welcome to the R_tech", true); 
		
		return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
	}

}
