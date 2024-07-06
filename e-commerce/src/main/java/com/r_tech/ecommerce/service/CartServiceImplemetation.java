package com.r_tech.ecommerce.service;

import org.springframework.stereotype.Service;

import com.r_tech.ecommerce.DAO.CartRepository;
import com.r_tech.ecommerce.exception.ProductException;
import com.r_tech.ecommerce.model.Cart;
import com.r_tech.ecommerce.model.User;
import com.r_tech.ecommerce.request.AddItemRequest;

@Service
public class CartServiceImplemetation implements CartService {
	
	private CartRepository cartRpository;
	
	private CartItemService cartItemService;

	@Override
	public Cart createCart(User user) {
	
		return null;
	}

	@Override
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cart findUserCart() {
		// TODO Auto-generated method stub
		return null;
	}

}
