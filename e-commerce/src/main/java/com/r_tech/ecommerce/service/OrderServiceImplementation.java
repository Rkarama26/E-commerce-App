package com.r_tech.ecommerce.service;

import com.r_tech.ecommerce.repository.*;
import com.r_tech.ecommerce.domain.OrderStatus;
import com.r_tech.ecommerce.domain.PaymentStatus;
import com.r_tech.ecommerce.exception.OrderNotFoundException;
import com.r_tech.ecommerce.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImplementation implements OrderService {

    private CartRepository cartRepository;
    private CartItemService cartitemService;
    private ProductService productService;
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private CartService cartService;
    private OrderItemRepository orderItemRepository;

    public OrderServiceImplementation(AddressRepository addressRepository, CartItemService cartitemService, CartRepository cartRepository, CartService cartService, OrderItemRepository orderItemRepository, OrderRepository orderRepository, ProductService productService, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.cartitemService = cartitemService;
        this.cartRepository = cartRepository;
        this.cartService = cartService;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.userRepository = userRepository;
    }

    @Override
    public Order createOrder(User user, Address shippingAddress) {

        log.info("the shipping address first name: {} ",shippingAddress.getFirstName());

        shippingAddress.setUser(user);
        Address address = addressRepository.save(shippingAddress);
        user.getAddresses().add(address);
        userRepository.save(user);

        Cart cart = cartService.findUserCart(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem item : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();

            orderItem.setPrice(item.getPrice());
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
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
        createdOrder.setOrderStatus(OrderStatus.PENDING);
        createdOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);
        createdOrder.setCreatedAt(LocalDateTime.now());

        Order saveedOrder = orderRepository.save(createdOrder);

        for (OrderItem item : orderItems) {
            item.setOrder(saveedOrder);
            orderItemRepository.save(item);
        }
        return saveedOrder;
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderNotFoundException {
        Order order=findOrderById(orderId);
        order.setOrderStatus(OrderStatus.PLACED);
        order.getPaymentDetails().setStatus(PaymentStatus.COMPLETED);
        return order;
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderNotFoundException {
        Order order=findOrderById(orderId);
        order.setOrderStatus(OrderStatus.CONFIRMED);


        return orderRepository.save(order);
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderNotFoundException {
        Order order=findOrderById(orderId);
        order.setOrderStatus(OrderStatus.SHIPPED);
        return orderRepository.save(order);
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderNotFoundException {
        Order order=findOrderById(orderId);
        order.setOrderStatus(OrderStatus.DELIVERED);
        return orderRepository.save(order);
    }

    @Override
    public Order cancledOrder(Long orderId) throws OrderNotFoundException {
        Order order=findOrderById(orderId);
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderNotFoundException {
        Optional<Order> opt=orderRepository.findById(orderId);

        if(opt.isPresent()) {
            return opt.get();
        }
        throw new OrderNotFoundException("order not exist with id "+orderId);
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) {
        List<Order> orders=orderRepository.getUsersOrders(userId);
        return orders;
    }

    @Override
    public List<Order> getAllOrders() {

        return orderRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderNotFoundException {
        Order order =findOrderById(orderId);

        orderRepository.deleteById(orderId);

    }


}
