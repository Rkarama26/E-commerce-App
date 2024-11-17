package com.r_tech.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r_tech.ecommerce.exception.CartItemException;
import com.r_tech.ecommerce.exception.UserException;
import com.r_tech.ecommerce.model.CartItem;
import com.r_tech.ecommerce.model.User;
import com.r_tech.ecommerce.response.ApiResponse;
import com.r_tech.ecommerce.service.CartItemService;
import com.r_tech.ecommerce.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/cart_item")
@Tag(name="Cart Item Management", description = "create cart item delete cart item")
public class CartItemController {
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private UserService userService;
	
	
	
	
	@DeleteMapping("/{cartItemId}")
	public ResponseEntity<ApiResponse> removeCartItem(@PathVariable Long cartItemId,
			@RequestHeader("Authorization") String jwt) throws CartItemException, UserException {

		//System.out.println("cartItemId: " + cartItemId);
		//System.out.println("Request headers: " + jwt);

		if (cartItemId == null) {
			throw new IllegalArgumentException("cartItemId cannot be null in controller");
		}

		User user = userService.findUserProfileByJwt(jwt);
		

		cartItemService.removeCartItem(user.getId(), cartItemId);

		ApiResponse res = new ApiResponse("cart item removed successfully", true);

		return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{cartItemId}")
	public ResponseEntity<CartItem>updateCartItemHandler(@PathVariable Long cartItemId, 
			@RequestBody CartItem cartItem, @RequestHeader("Authorization")String jwt) throws CartItemException, UserException{
		
		User user = userService.findUserProfileByJwt(jwt);
		
		CartItem updatedCartItem =  cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
		
		
		return new ResponseEntity<>(updatedCartItem, HttpStatus.ACCEPTED);
	}


}
