package com.r_tech.ecommerce.controller;

import java.util.List;

import com.r_tech.ecommerce.exception.ResourceNotFoundException;
import com.r_tech.ecommerce.model.Category;
import com.r_tech.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.r_tech.ecommerce.exception.ProductException;
import com.r_tech.ecommerce.model.Product;
import com.r_tech.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	private ProductService productService;

	private CategoryRepository categoryRepository;

	public ProductController(CategoryRepository categoryRepository, ProductService productService) {
		this.categoryRepository = categoryRepository;
		this.productService = productService;
	}

	@GetMapping("/products")
	public ResponseEntity<Page<Product>>findProductByCategoryHandler(@RequestHeader("Authorization") String jwt,  @RequestParam String category,
																	 @RequestParam Integer minPrice, @RequestParam Integer maxPrice,
																	 @RequestParam Integer minDiscount, @RequestParam String sort , @RequestParam String stock,
																	 @RequestParam Integer pageNumber, @RequestParam Integer pageSize ){
		
		Page<Product> res = productService.getAllProduct(category,
			minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);
		
		System.out.println("complete products");
		
		return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/products/id/{productId}")
	public ResponseEntity<Product> findProductById(@RequestHeader("Authorization") String jwt, @PathVariable Long productId )throws ProductException{
		
		Product product = productService.findProductById(productId);
		
		
		return new ResponseEntity<Product>(product, HttpStatus.ACCEPTED);
		
	}


	@GetMapping("/products/{categoryName}")
	public ResponseEntity<List<Product>> getProductsByAnyCategory( @RequestHeader("Authorization") String jwt, @PathVariable String categoryName) throws ResourceNotFoundException {

		if (categoryName == null || categoryName.trim().isEmpty()) {
			throw new IllegalArgumentException("Category name cannot be null or empty");
		}
		List<Product> products = productService.findProductByCategory(categoryName);
		return ResponseEntity.ok(products);
	}





}
