package com.r_tech.ecommerce.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.r_tech.ecommerce.model.Cart;
import com.r_tech.ecommerce.model.CartItem;
import com.r_tech.ecommerce.model.Product;
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	@Query("SELECT ci FROM CartItem ci Where ci.cart=:cart AND ci.product=:product  AND ci.userId=:userId ")
	public CartItem isCartItemExist(@Param("cart")Cart cart, @Param("product")Product product, @Param("userId") Long userId);

}
