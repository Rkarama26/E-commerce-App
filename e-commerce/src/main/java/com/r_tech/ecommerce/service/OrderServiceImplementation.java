package com.r_tech.ecommerce.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.r_tech.ecommerce.DAO.AddressRepository;
import com.r_tech.ecommerce.DAO.CartRepository;
import com.r_tech.ecommerce.DAO.OrderItemRepository;
import com.r_tech.ecommerce.DAO.OrderRepository;
import com.r_tech.ecommerce.DAO.UserRepository;
import com.r_tech.ecommerce.exception.OrderException;
import com.r_tech.ecommerce.model.Address;
import com.r_tech.ecommerce.model.Cart;
import com.r_tech.ecommerce.model.CartItem;
import com.r_tech.ecommerce.model.Order;
import com.r_tech.ecommerce.model.OrderItem;
import com.r_tech.ecommerce.model.User;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImplementation implements OrderService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Transactional
	public Order createOrder(User user, Address shippAddress) {

		shippAddress.setUser(user);
		Address address = addressRepository.save(shippAddress);
		user.getAddresses().add(address);
		userRepository.save(user);

		Cart cart = cartService.findUserCart(user.getId());
		List<OrderItem> orderItems = new ArrayList<>();

		for (CartItem item : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();

			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSize(item.getSize());
			orderItem.setUserId(item.getUserId());
			orderItem.setDiscountedPrice(item.getDiscountedPrice());

			OrderItem createdOrderItem = orderItemRepository.save(orderItem);

			orderItems.add(createdOrderItem);
		}

		Order createdOrder = new Order();
		createdOrder.setUser(user);
		createdOrder.setOrderItem(orderItems);
		createdOrder.setTotalPrice(cart.getTotalPrice());
		createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
		createdOrder.setDiscount(cart.getDiscount());
		createdOrder.setTotalItem(cart.getTotalItem());

		createdOrder.setShippingAddress(address);
		createdOrder.setOrderDate(LocalDateTime.now());
		createdOrder.setOrderStatus("PENDING");
		createdOrder.getPaymentDetails().setStatus("PENDING");
		createdOrder.setCreatedAt(LocalDateTime.now());

		Order savedOrder = orderRepository.save(createdOrder);

		for (OrderItem item : orderItems) {
			item.setOrder(savedOrder);
			orderItemRepository.save(item);
		}

		return savedOrder;

	}

	@Override
	public Order placedOrder(Long orderId) throws OrderException {

		Order order = findOrderById(orderId);
		order.setOrderStatus("PLACED");
		order.getPaymentDetails().setStatus("COMPLETED");

		return order;
	}

	@Override
	public Order confirmOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("CONFIRMED");
		return orderRepository.save(order);
	}

	@Override
	public Order shippedOrder(Long orderId) throws OrderException {

		Order order = findOrderById(orderId);
		order.setOrderStatus("SHIPPED");
		return orderRepository.save(order);
	}

	@Override
	public Order deliverOrder(Long orderId) throws OrderException {

		Order order = findOrderById(orderId);
		order.setOrderStatus("DELIVERED");
		return orderRepository.save(order);
	}

	@Override
	public Order cancelOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("CANCELLED");
		return orderRepository.save(order);
	}

	@Override
	public Order findOrderById(Long orderId) throws OrderException {
		Optional<Order> opt = orderRepository.findById(orderId);
		if (opt.isPresent()) {
			return opt.get();
		}
		throw new OrderException("order not exist with id " + orderId);
	}

	@Override
	public List<Order> usersOrderHistory(Long userId) throws OrderException {
		List<Order> orders = orderRepository.getUsersOrders(userId);
		return orders;
	}

	@Override
	public List<Order> getAllOrder() {
		return orderRepository.findAllByOrderByCreatedAtDesc();
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		orderRepository.delete(order);

	}

}
