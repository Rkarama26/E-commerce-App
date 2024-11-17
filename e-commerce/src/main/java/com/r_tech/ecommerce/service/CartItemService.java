package com.r_tech.ecommerce.service;

import com.r_tech.ecommerce.exception.CartItemException;
import com.r_tech.ecommerce.exception.UserException;
import com.r_tech.ecommerce.model.Cart;
import com.r_tech.ecommerce.model.CartItem;
import com.r_tech.ecommerce.model.Product;

public interface CartItemService {

	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(Long userId , Long id , CartItem cartItem) throws CartItemException, UserException;
	
	public CartItem isCardItemExist(Cart cart, Product product, Long userId);
	
	public void removeCartItem(Long UserId, Long cartItemId)throws CartItemException, UserException;
	
	public CartItem findCartItemById(Long cartItemId) throws CartItemException;

	
}
