package com.r_tech.ecommerce.service;

import java.util.List;

import com.r_tech.ecommerce.exception.ProductException;
import com.r_tech.ecommerce.model.Rating;
import com.r_tech.ecommerce.model.User;
import com.r_tech.ecommerce.request.RatingRequest;

public interface RatingService {
	
	public Rating createRating(RatingRequest req, User user) throws ProductException;
	
	
	public List<Rating> getProductsRating(Long productId);
	
	
	
	

}
