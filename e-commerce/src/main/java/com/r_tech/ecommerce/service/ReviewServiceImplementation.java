package com.r_tech.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.r_tech.ecommerce.DAO.ReviewRepository;
import com.r_tech.ecommerce.exception.ProductException;
import com.r_tech.ecommerce.model.Product;
import com.r_tech.ecommerce.model.Review;
import com.r_tech.ecommerce.model.User;
import com.r_tech.ecommerce.request.ReviewRequest;

@Service
public class ReviewServiceImplementation implements ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private ProductService productService;

	@Override
	public Review createReview(ReviewRequest req, User user) throws ProductException {
		
		Product product = productService.findProductById(req.getProductId());
		
		Review review = new Review();
		
		review.setProduct(product);
		review.setUser(user);
		review.setReview(req.getReview());
		review.setCreatedAt(LocalDateTime.now());
		
		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getAllReview(Long productId) {
		
		return reviewRepository.getAllProductsReview(productId);
	}

}
