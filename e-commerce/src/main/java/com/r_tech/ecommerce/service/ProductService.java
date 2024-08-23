package com.r_tech.ecommerce.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.r_tech.ecommerce.exception.ProductException;
import com.r_tech.ecommerce.model.Product;
import com.r_tech.ecommerce.request.CreateProductRequest;

public interface ProductService {
	
	public Product createProduct(CreateProductRequest req);
	
	public String deleteproduct(Long productId)throws ProductException;
	
	public Product updateProduct(Long productId, Product reg) throws ProductException;
	
	public Product findProductById(Long id)throws ProductException;
	
	public List<Product> findProductByCategory(String category);
	
	public Page<Product>getAllProduct(String category, Integer minPrice, Integer maxPrice,
			 Integer minDiscount, String sort , String stock, Integer pageNumber, Integer pageSize);

	public List<Product> findProducts();



}
