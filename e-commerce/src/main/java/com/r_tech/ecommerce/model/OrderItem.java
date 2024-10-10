package com.r_tech.ecommerce.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	

	@ManyToOne
	@JsonIgnore
	private Order order;
	
	@ManyToOne
	private Product product;
	
	private String size;
	
	private Integer quantity;
	
	private Integer price;
	
	private Long userId;
	
	private LocalDateTime  deliveryDate;
	
	private Integer discountedPrice;
	


	
}
