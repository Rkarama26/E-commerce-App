package com.r_tech.ecommerce.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.r_tech.ecommerce.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.r_tech.ecommerce.repository.CategoryRepository;
import com.r_tech.ecommerce.repository.ProductRepository;
import com.r_tech.ecommerce.exception.ProductException;
import com.r_tech.ecommerce.model.Category;
import com.r_tech.ecommerce.model.Product;
import com.r_tech.ecommerce.request.CreateProductRequest;

@Service
@Slf4j
public class ProductServiceImplementation implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	

	//API - Create Product 
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
		//product.(req.getProductType());
		product.setQuantity(req.getQuantity());
		product.setCreatedAt(LocalDateTime.now());
		product.setDatasheet(req.getDatasheet());
		product.setDiscountedPrice(req.getDiscountedPrice());

		Product savedProduct = productRepository.save(product);

		return savedProduct;
	}

	// API - Delete Product 
	@Override
	public void deleteproduct(Long productId) throws ProductException {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ProductException("Product not found with ID: " + productId));


		productRepository.delete(product);
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
  
	// API  - Find Product
	@Override
	public Product findProductById(Long id) throws ProductException {

		Optional<Product> opt = productRepository.findById(id);

		if (opt.isPresent()) {
			return opt.get();
		}
		throw new ProductException("Product not found with id-" + id);
	}

	
	@Override
	public List<Product> findProductByCategory(String categoryName) throws ResourceNotFoundException {
		log.info("Fetching products for category: {}", categoryName);

		// Find the category by name
		Category category = categoryRepository.findFirstByName(categoryName)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found: " + categoryName));
		if (category == null) {
			throw new ResourceNotFoundException("Category not found: " + categoryName);
		}

		// Get all categories including subcategories
		List<Category> allCategories = new ArrayList<>();
		collectAllCategories(category, allCategories);

		// Fetch products for all found categories
		return productRepository.findByCategoryIn(allCategories);
	}

	@Override
	public Page<Product> getAllProduct(String category, Integer minPrice, Integer maxPrice,
			Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {

		//using pagination it will show page by pageNumber
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		
		List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice,minDiscount, sort);
		// filtering on the based of product type 
//		if (!productType.isEmpty()) {
//		    products = products.stream()
//		                       .filter(p -> p.getProductType().stream()
//		                                   .anyMatch(c -> productType.stream()
//		                                   .anyMatch(c1 -> c1.equalsIgnoreCase(c))))
//		                                   .collect(Collectors.toList());
//
//		}
		
		if (stock != null) {
			if(stock.equals("in_stock")) {
				
			products = products.stream().filter(p -> p.getQuantity()>0).collect(Collectors.toList());
			}
			else if(stock.equals("out_of_stock")) {
				products = products.stream().filter(p ->p.getQuantity()<1).collect(Collectors.toList());
			}
		}

        int startIndex = (int)pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());
	
		
		List<Product> pageContent = products.subList(startIndex, endIndex);
		Page<Product> filterProducts = new PageImpl<>(pageContent, pageable, products.size());
		
		return filterProducts;
	}

	@Override
	public List<Product> findProducts() {
		
		return productRepository.findAll();
	}

	private void collectAllCategories(Category category, List<Category> allCategories) {
		allCategories.add(category); // Add the current category

		// Fetch all subcategories recursively
		List<Category> subcategories = categoryRepository.findSubcategories(category);
		for (Category subcategory : subcategories) {
			collectAllCategories(subcategory, allCategories);
		}
	}



//	public List<Product> getProductsByAnyCategory(String categoryName) throws ResourceNotFoundException {
//		Category category = categoryRepository.findByName(categoryName);
//		if (category == null) {
//			throw new ResourceNotFoundException("Category not found: " + categoryName);
//		}
//
//		List<Category> allSubCategories = getAllSubCategories(category);
//		return productRepository.findByCategoryIn(allSubCategories);
//	}
//
//
//	private List<Category> getAllSubCategories(Category category) {
//		List<Category> subCategories = categoryRepository.findAllByParentCategory(category);
//		List<Category> allCategories = new ArrayList<>(subCategories);
//
//		for (Category subCategory : subCategories) {
//			allCategories.addAll(getAllSubCategories(subCategory));
//		}
//
//		allCategories.add(category); // Include the given category itself
//		return allCategories;
//	}
}
