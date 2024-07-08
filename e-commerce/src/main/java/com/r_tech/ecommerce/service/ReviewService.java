package com.r_tech.ecommerce.service;

import java.util.List;

import com.r_tech.ecommerce.exception.ProductException;
import com.r_tech.ecommerce.model.Review;
import com.r_tech.ecommerce.model.User;
import com.r_tech.ecommerce.request.ReviewRequest;

public interface ReviewService {
	
	public Review createReview(ReviewRequest req, User user) throws ProductException;

	public List<Review> getAllReview(Long productId);
}
