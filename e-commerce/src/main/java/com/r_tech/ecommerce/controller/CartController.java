package com.r_tech.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r_tech.ecommerce.exception.CartItemException;
import com.r_tech.ecommerce.exception.ProductException;
import com.r_tech.ecommerce.exception.UserException;
import com.r_tech.ecommerce.model.Cart;
import com.r_tech.ecommerce.model.CartItem;
import com.r_tech.ecommerce.model.User;
import com.r_tech.ecommerce.request.AddItemRequest;
import com.r_tech.ecommerce.response.ApiResponse;
import com.r_tech.ecommerce.service.CartItemService;
import com.r_tech.ecommerce.service.CartService;
import com.r_tech.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart API's", description = "Find Cart, Add Items, Remove Items")
public class CartController {

	@Autowired
	private CartService cartService;
	@Autowired
	private UserService userService;
	@Autowired
	private CartItemService cartItemService;

	@GetMapping("/")
	@Operation(description = "find cart by user id")
	public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException {

		User user = userService.findUserProfileByJwt(jwt);

		Cart cart = cartService.findUserCart(user.getId());

		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}

	@PutMapping("/add")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req,
			@RequestHeader("Authorization") String jwt) throws UserException, ProductException {

		System.out.println("Request body: " + req);
		System.out.println("req.getProductId(): " + req.getProductId());
		System.out.println("req.getQuantity(): " + req.getQuantity());
		System.out.println("Request headers: " + jwt);

		if (req.getProductId() == null) {
			throw new IllegalArgumentException("productId cannot be null in cotroller");
		}

		User user = userService.findUserProfileByJwt(jwt);

		System.out.print("controller called");

		cartService.addCartItem(user.getId(), req);

		ApiResponse res = new ApiResponse("item added to cart", true);

		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@DeleteMapping("/cart_item/{cartItemId}")
	public ResponseEntity<ApiResponse> removeCartItem(@PathVariable Long cartItemId,
			@RequestHeader("Authorization") String jwt) throws CartItemException, UserException {

		System.out.println("cartItemId: " + cartItemId);
		System.out.println("Request headers: " + jwt);

		if (cartItemId == null) {
			throw new IllegalArgumentException("cartItemId cannot be null in controller");
		}

		User user = userService.findUserProfileByJwt(jwt);
		Long userId = user.getId();

		cartItemService.removeCartItem(userId, cartItemId);

		ApiResponse res = new ApiResponse("cart item removed successfully", true);

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping("/cart_item/{cartItemId}")
	public ResponseEntity<CartItem> updateCartItemQuantity(@PathVariable Long cartItemId,
			@RequestBody Integer quantityChange) throws CartItemException { // Directly accept Integer
		
		CartItem updatedCartItem = cartItemService.updateCartItemQuantity(cartItemId, quantityChange);
		return ResponseEntity.ok(updatedCartItem);
	}

}
