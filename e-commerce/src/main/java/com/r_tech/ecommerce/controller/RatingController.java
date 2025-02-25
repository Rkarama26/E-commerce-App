package com.r_tech.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r_tech.ecommerce.exception.ProductException;
import com.r_tech.ecommerce.exception.UserException;
import com.r_tech.ecommerce.model.Rating;
import com.r_tech.ecommerce.model.User;
import com.r_tech.ecommerce.request.RatingRequest;
import com.r_tech.ecommerce.service.RatingService;
import com.r_tech.ecommerce.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/ratings")
@Tag(name = "Rating API's")
public class RatingController {

	private UserService userService;
	private RatingService ratingService;

	public RatingController(RatingService ratingService, UserService userService) {
		this.ratingService = ratingService;
		this.userService = userService;
	}

	@PostMapping("/create")
	public ResponseEntity<Rating> createRating(@RequestBody RatingRequest req, 
			  @RequestHeader("Authorization")String jwt) throws UserException , ProductException{
		
		User user = userService.findUserProfileByJwt(jwt);
		
		Rating rating = ratingService.createRating(req, user);
		
		return new ResponseEntity<>(rating, HttpStatus.CREATED);
		
	}
	
	@GetMapping("product/{productId}")
	public ResponseEntity<List<Rating>> getProductsRating(@PathVariable Long productId, 
			      @RequestHeader("Authorization") String jwt) throws UserException, ProductException{
		
		User user = userService.findUserProfileByJwt(jwt);
		
	List<Rating> ratings = ratingService.getProductsRating(productId);
	
	return new ResponseEntity<>(ratings, HttpStatus.CREATED);
	
		
		
		
	}

}
