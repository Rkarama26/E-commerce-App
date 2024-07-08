package com.r_tech.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.r_tech.ecommerce.DAO.CartRepository;
import com.r_tech.ecommerce.exception.ProductException;
import com.r_tech.ecommerce.model.Cart;
import com.r_tech.ecommerce.model.CartItem;
import com.r_tech.ecommerce.model.Product;
import com.r_tech.ecommerce.model.User;
import com.r_tech.ecommerce.request.AddItemRequest;

@Service
public class CartServiceImplemetation implements CartService {

	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private ProductService productService;
	
	

	@Override
	public Cart createCart(User user) {

		Cart cart = new Cart();
		cart.setUser(user);

		return cartRepository.save(cart);

	}

	@Override
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {

		Cart cart = cartRepository.findByUserId(userId);
		Product product = productService.findProductById(req.getProductId());

		CartItem isPresent = cartItemService.isCardItemExist(cart, product, userId);

		if (isPresent == null) {
			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQuantity(req.getQuantity());
			cartItem.setUserId(userId);

			int price = req.getQuantity() * product.getPrice();
			cartItem.setPrice(price);
			cartItem.setSize(req.getSize());

			CartItem createCartItem = cartItemService.createCartItem(cartItem);

			cart.getCartItems().add(createCartItem);
		}

		return "Item Added To Cart";

	}

	@Override
	public Cart findUserCart(Long userId) {

		Cart cart = cartRepository.findByUserId(userId);

		int totalPrice = 0;
		int totalDiscountedPrice = 0;
		int totalItem = 0;

		for (CartItem cartItem : cart.getCartItems()) {
			totalPrice = totalPrice + cartItem.getPrice();
			totalDiscountedPrice = totalDiscountedPrice + cartItem.getDiscountedPrice();
			totalItem = totalItem + cartItem.getQuantity();
		}

		cart.setTotalDiscountedPrice(totalDiscountedPrice);
		cart.setTotalItem(totalItem);
		cart.setTotalPrice(totalPrice);
		cart.setDiscount(totalPrice - totalDiscountedPrice);

		return cartRepository.save(cart);
	}

}
