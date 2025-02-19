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
import lombok.Data;

@Entity
@Data
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

	private LocalDateTime createdAt;


}
