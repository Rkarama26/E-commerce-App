package com.r_tech.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r_tech.ecommerce.exception.OrderException;
import com.r_tech.ecommerce.model.Order;
import com.r_tech.ecommerce.response.ApiResponse;
import com.r_tech.ecommerce.service.OrderService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/admin/orders")
@Tag(name = "Admin Order API's")
public class AdminOrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/")
	public ResponseEntity<List<Order>> getAllOrdersHandler() {

		List<Order> orders = orderService.getAllOrder();

		return new ResponseEntity<List<Order>>(orders, HttpStatus.ACCEPTED);

	}

	@PutMapping("/{orderId}/confirmed")
	public ResponseEntity<Order> confirmedOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {
		Order order = orderService.confirmOrder(orderId);

		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}

	@PutMapping("/{orderId}/ship")
	public ResponseEntity<Order> shippedOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {
		Order order = orderService.shippedOrder(orderId);

		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	@PutMapping("/{orderId}/deliver")
	public ResponseEntity<Order> deliverOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {
		Order order = orderService.deliverOrder(orderId);

		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	@PutMapping("/{orderId}/cancel")
	public ResponseEntity<Order> cancelOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {
		Order order = orderService.cancelOrder(orderId);

		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	@PutMapping("/{orderId}/delete")
	public ResponseEntity<ApiResponse> DeleteOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {

		orderService.deleteOrder(orderId);

		ApiResponse res = new ApiResponse();
		res.setMessage("Order deleted successfully");
		res.setStatus(true);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
