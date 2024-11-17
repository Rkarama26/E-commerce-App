package com.r_tech.ecommerce.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.r_tech.ecommerce.exception.ProductException;
import com.r_tech.ecommerce.model.Product;
import com.r_tech.ecommerce.service.ProductService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Product API's")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public ResponseEntity<Page<Product>>findProductByCategoryHandler(
			@RequestParam String category,
			@RequestParam Integer minPrice, 
			@RequestParam Integer maxPrice,
			@RequestParam Integer minDiscount,
			@RequestParam String sort ,
			@RequestParam String stock,
			@RequestParam Integer pageNumber,
			@RequestParam Integer pageSize
			)
	{
		
		Page<Product> res = productService.getAllProduct(category,
			minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);
		
		System.out.println("complete products");
		
		return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/product/id/{productId}")
	public ResponseEntity<Product> findProductById(@PathVariable Long productId )throws ProductException{
		
		Product product = productService.findProductById(productId);
		
		
		return new ResponseEntity<Product>(product, HttpStatus.ACCEPTED);
		
	}
	
	
	
	

}
