package com.r_tech.ecommerce.service;

import java.util.List;

import com.r_tech.ecommerce.exception.OrderException;
import com.r_tech.ecommerce.model.Address;
import com.r_tech.ecommerce.model.Order;
import com.r_tech.ecommerce.model.User;

public interface OrderService {
	
	
	public Order createOrder(User user, Address shippingAddress);
    
	public Order findOrderById(Long orderId) throws OrderException;
	
	public List<Order> usersOrderHistory(Long userId) throws OrderException;
	
	//admin
	public Order placedOrder(Long orderId) throws OrderException;
	//admin
	public Order confirmOrder(Long orderId) throws OrderException;
	//admin 
	public Order shippedOrder(Long orderId) throws OrderException;
	//admin
	public Order deliverOrder(Long orderId) throws OrderException;
	// user, admin
	public Order cancelOrder(Long orderId) throws OrderException;
	//admin
	public List<Order> getAllOrder(Long orderId);
	//admin
	public void deleteOrder(Long orderId) throws OrderException;
	
}
