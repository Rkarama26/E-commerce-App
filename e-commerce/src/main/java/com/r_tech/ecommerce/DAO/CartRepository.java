package com.r_tech.ecommerce.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.r_tech.ecommerce.model.Cart;
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	@Query("SELECT  c FROM Cart c WHERE c.user.id =:userId")
	Cart findByUserId(@Param("userId")Long userId);

}
