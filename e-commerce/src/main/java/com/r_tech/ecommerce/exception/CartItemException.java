package com.r_tech.ecommerce.exception;

public class CartItemException extends Exception {

	public CartItemException() {
		super("Item Not Found");
	}
	
	public CartItemException(String message) {
		super(message);
	}

	public CartItemException(String message, Long cartItemId) {
		super(message + " (ID: " + cartItemId + ")");
		// TODO Auto-generated constructor stub
	}

	

	
}
