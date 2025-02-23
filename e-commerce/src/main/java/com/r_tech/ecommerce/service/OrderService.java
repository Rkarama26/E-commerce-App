package com.r_tech.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.r_tech.ecommerce.exception.OrderNotFoundException;
import com.r_tech.ecommerce.model.Address;
import com.r_tech.ecommerce.model.Order;
import com.r_tech.ecommerce.model.User;

@Service
public interface OrderService {


	public Order createOrder(User user, Address shippingAdress);

	public Order findOrderById(Long orderId) throws OrderNotFoundException;

	public List<Order> usersOrderHistory(Long userId);

	public Order placedOrder(Long orderId) throws OrderNotFoundException;

	public Order confirmedOrder(Long orderId)throws OrderNotFoundException;

	public Order shippedOrder(Long orderId) throws OrderNotFoundException;

	public Order deliveredOrder(Long orderId) throws OrderNotFoundException;

	public Order cancledOrder(Long orderId) throws OrderNotFoundException;

	public List<Order>getAllOrders();

	public void deleteOrder(Long orderId) throws OrderNotFoundException;

}
