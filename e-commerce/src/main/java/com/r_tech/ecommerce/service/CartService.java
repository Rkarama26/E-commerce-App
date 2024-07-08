package com.r_tech.ecommerce.service;



import com.r_tech.ecommerce.exception.ProductException;
import com.r_tech.ecommerce.model.Cart;
import com.r_tech.ecommerce.model.User;
import com.r_tech.ecommerce.request.AddItemRequest;

public interface CartService {
	
	public Cart createCart(User user);
	
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException;
	

	public Cart findUserCart(Long userId);

}
