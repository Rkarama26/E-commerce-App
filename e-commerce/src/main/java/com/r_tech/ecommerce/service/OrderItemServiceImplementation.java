package com.r_tech.ecommerce.service;

import com.r_tech.ecommerce.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.r_tech.ecommerce.model.OrderItem;

@Service
public class OrderItemServiceImplementation implements OrderItemService {

	private OrderItemRepository orderItemRepository;

	public OrderItemServiceImplementation(OrderItemRepository orderItemRepository) {
		this.orderItemRepository = orderItemRepository;
	}

	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		
		return orderItemRepository.save(orderItem);
	}
	
	
	
}
