package com.r_tech.ecommerce.service;

import org.springframework.stereotype.Service;

import com.r_tech.ecommerce.model.OrderItem;


public interface OrderItemService {
	
	public OrderItem createOrderItem(OrderItem orderItem);

}
