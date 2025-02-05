package com.r_tech.ecommerce.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String title;

	private String description;

	private int price;

	@Column(name = "discount_price")
	private int discountedPrice;

	@Column(name = "discount_percent")
	private int discountPercent;

	@Column(name = "quantity")
	private int quantity;



	@ElementCollection
	@CollectionTable(name = "product_features", joinColumns = @JoinColumn(name = "product_id"))
	@Column(name = "feature")
	private List<String> features;

	@ElementCollection
	@CollectionTable(name = "product_specifications", joinColumns = @JoinColumn(name = "product_id"))
	@Column(name = "specification")
	private List<String> specification;

	@Column(name = "image_url")
	private String imageUrl;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Rating> rating = new ArrayList<>();

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Review> reviews = new ArrayList<>();

	@Column(name = "num_ratings")
	private int numRatings;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@Lob
	private byte[] datasheet;

	

	public byte[] getDatasheet() {
		return datasheet;
	}

	public void setDatasheet(byte[] datasheet) {
		this.datasheet = datasheet;
	}

	private LocalDateTime createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(int discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	

	public int getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(int discountPercent) {
		this.discountPercent = discountPercent;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public List<String> getFeatures() {
		return features;
	}

	public void setFeatures(List<String> features) {
		this.features = features;
	}

	public List<String> getSpecification() {
		return specification;
	}

	public void setSpecification(List<String> specification) {
		this.specification = specification;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<Rating> getRating() {
		return rating;
	}

	public void setRating(List<Rating> rating) {
		this.rating = rating;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public int getNumRatings() {
		return numRatings;
	}

	public void setNumRatings(int numRatings) {
		this.numRatings = numRatings;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public Product() {
		// TODO Auto-generated constructor stub
	}

	public Product(Long id, String title, String description, int price, int discountedPrice, int disountedPersent,
			int quantity, List<String> features, List<String> specification, String imageUrl,
			List<Rating> rating, List<Review> reviews, int numRatings, Category category, byte[] datasheet,
			LocalDateTime createdAt) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.price = price;
		this.discountedPrice = discountedPrice;
		this.discountPercent = disountedPersent;
		this.quantity = quantity;
		this.features = features;
		this.specification = specification;
		this.imageUrl = imageUrl;
		this.rating = rating;
		this.reviews = reviews;
		this.numRatings = numRatings;
		this.category = category;
		this.datasheet = datasheet;
		this.createdAt = createdAt;
	}

}
