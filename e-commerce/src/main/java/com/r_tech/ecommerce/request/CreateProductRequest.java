package com.r_tech.ecommerce.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CreateProductRequest {

	private String title;
	
	private String description;
	
	private int price;
	
	private int discountedPrice;
	
	private int discountPercent;
	
	private int quantity;

	private List<String> features; // Change to List<String>
	
    private List<String> specification; // Change to List<String>
	
	private byte[] datasheet;

	private String imageUrl;
	
	private String topLavelCategory;
	
	private String secondLavelCategory;
	
	private String thirdLavelCategory;
	
	private int numRatings;
	
	private int createdAt;


}
