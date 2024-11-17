package com.r_tech.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.r_tech.ecommerce.DAO.CategoryRepository;
import com.r_tech.ecommerce.DAO.ProductRepository;
import com.r_tech.ecommerce.exception.ProductException;
import com.r_tech.ecommerce.model.Category;
import com.r_tech.ecommerce.model.Product;
import com.r_tech.ecommerce.request.CreateProductRequest;

@Service
public class ProductServiceImplementation implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	// API - Create Product
	@Override
	public Product createProduct(CreateProductRequest req) {

		Category topLavel = categoryRepository.findByName(req.getTopLavelCategory());

		if (topLavel == null) {
			Category topLavelCategory = new Category();
			topLavelCategory.setName(req.getTopLavelCategory());
			topLavelCategory.setLevel(1);

			topLavel = categoryRepository.save(topLavelCategory);
		}
		Category secondLevel = categoryRepository.findByNameAndParent(req.getSecondLavelCategory(), topLavel.getName());

		if (secondLevel == null) {
			Category secondLavelCategory = new Category();
			secondLavelCategory.setName(req.getSecondLavelCategory());
			secondLavelCategory.setParentCategory(topLavel);
			secondLavelCategory.setLevel(2);

			secondLevel = categoryRepository.save(secondLavelCategory);
		}

		Category thirdLevel = categoryRepository.findByNameAndParent(req.getThirdLavelCategory(),
				secondLevel.getName());
		if (thirdLevel == null) {
			Category thirdLavelCategory = new Category();
			thirdLavelCategory.setName(req.getThirdLavelCategory());
			thirdLavelCategory.setParentCategory(secondLevel);
			thirdLavelCategory.setLevel(3);

			thirdLevel = categoryRepository.save(thirdLavelCategory);
		}

		Product product = new Product();

		product.setTitle(req.getTitle());
		product.setCategory(thirdLevel);
		product.setDescription(req.getDescription());
		product.setFeatures(req.getFeatures());
		product.setSpecification(req.getSpecification());
		product.setDiscountedPrice(req.getDiscountedPrice());
		product.setDiscountPercent(req.getDiscountPercent());
		product.setPrice(req.getPrice());
		product.setImageUrl(req.getImageUrl());
		product.setNumRatings(req.getNumRatings());
		product.setQuantity(req.getQuantity());
		product.setCreatedAt(LocalDateTime.now());
		product.setDatasheet(req.getDatasheet());
		product.setDiscountedPrice(req.getDiscountedPrice());

		Product savedProduct = productRepository.save(product);

		return savedProduct;
	}

	// API - Delete Product
	@Override
	public String deleteproduct(Long productId) throws ProductException {
		Product product = findProductById(productId);
		productRepository.delete(product);

		return "Product deleted Successfully";
	}

	// API - Update Product
	@Override
	public Product updateProduct(Long productId, Product req) throws ProductException {

		Product product = findProductById(productId);

		if (req.getQuantity() != 0) {
			product.setQuantity(req.getQuantity());
		}

		return productRepository.save(product);
	}

	// API - Find Product
	@Override
	public Product findProductById(Long id) throws ProductException {

		Optional<Product> opt = productRepository.findById(id);

		if (opt.isPresent()) {
			return opt.get();
		}
		throw new ProductException("Product not found with id-" + id);
	}

	@Override
	public List<Product> findProductByCategory(String category) {

		System.out.println("category --- " + category);

		List<Product> products = productRepository.findByCategory(category);

		return products;
	}

	@Override
	public Page<Product> getAllProduct(String category, Integer minPrice, Integer maxPrice, Integer minDiscount,
			String sort, String stock, Integer pageNumber, Integer pageSize) {

		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		System.out.println("Calling filterProducts with parameters: " + category + " , " + minPrice + ", " + maxPrice
				+ ", " + minDiscount + ", " + sort);

		List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);
		System.out.println("Products after filterProducts call: " + products.size() + " products");

		if (stock != null) {
			if (stock.equals("in_stock")) {
				products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
			} else if (stock.equals("out_of_stock")) {
				products = products.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
			}
			System.out.println("Products after stock filter: " + products.size() + " products");
		}

		int startIndex = (int) pageable.getOffset();
		int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());
		System.out.println("Pagination: startIndex=" + startIndex + ", endIndex=" + endIndex);

		List<Product> pageContent = products.subList(startIndex, endIndex);
		Page<Product> filterProducts = new PageImpl<>(pageContent, pageable, products.size());

		return filterProducts;
	}

	@Override
	public List<Product> findProducts() {

		return productRepository.findAll();
	}

}
