package com.r_tech.ecommerce.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.r_tech.ecommerce.model.Order;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	List<Order> findAllByOrderByCreatedAtDesc();

}
