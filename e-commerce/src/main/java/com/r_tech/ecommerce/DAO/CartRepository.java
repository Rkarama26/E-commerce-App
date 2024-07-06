package com.r_tech.ecommerce.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.r_tech.ecommerce.model.Cart;
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
