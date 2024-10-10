package com.r_tech.ecommerce.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private String password;
	
	private String email;
	
	private String role;
	
	private String mobile;
	
	@JsonIgnore
	@OneToMany(mappedBy="user", cascade =CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Address> addresses = new ArrayList<>();
	
	
	@Embedded
	@ElementCollection
	@CollectionTable(name="payment_Information", joinColumns  = @JoinColumn(name="user_id"))
	private List<PaymentInformation> paymentInformation  = new ArrayList<>();
	
	
	@OneToMany(mappedBy="user", cascade =CascadeType.ALL)
	@JsonIgnore
	private List<Rating> ratings = new ArrayList<>();
	
	
	@OneToMany(mappedBy="user", cascade =CascadeType.ALL)
	private List<Review> reviews = new ArrayList<>();
	
	private LocalDateTime createdAt;

	
	
	

}
