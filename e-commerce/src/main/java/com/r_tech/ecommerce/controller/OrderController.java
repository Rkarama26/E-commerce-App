package com.r_tech.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.r_tech.ecommerce.exception.OrderException;
import com.r_tech.ecommerce.exception.UserException;
import com.r_tech.ecommerce.model.Address;
import com.r_tech.ecommerce.model.Order;
import com.r_tech.ecommerce.model.User;
import com.r_tech.ecommerce.service.OrderService;
import com.r_tech.ecommerce.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order API'S")
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;

	@PostMapping("/")
	public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress,
			@RequestHeader("Authorization") String jwt) throws UserException {

		System.out.println("creaorderHndler");
		
		System.out.println("address--    " + shippingAddress.getFirstName());
	
		User user = userService.findUserProfileByJwt(jwt);

		Order order = orderService.createOrder(user, shippingAddress);

		//System.out.println("order" + order);

		return new ResponseEntity<Order>(order, HttpStatus.CREATED);
	}

	@GetMapping("/user")
	public ResponseEntity<List<Order>> usersOrderHistory(@RequestHeader("Authorization") String jwt)
			throws UserException, OrderException {

		User user = userService.findUserProfileByJwt(jwt);

		List<Order> orders = orderService.usersOrderHistory(user.getId());

		return new ResponseEntity<>(orders, HttpStatus.CREATED);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<Order> findOrderById(@PathVariable("orderId") Long orderId,
			@RequestHeader("Authorization") String jwt) throws UserException, OrderException {

		User user = userService.findUserProfileByJwt(jwt);

		Order order = orderService.findOrderById(orderId);

		return new ResponseEntity<>(order, HttpStatus.CREATED);
	}
}
