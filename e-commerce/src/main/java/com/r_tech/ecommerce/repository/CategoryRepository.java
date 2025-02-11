package com.r_tech.ecommerce.repository;

import com.r_tech.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.r_tech.ecommerce.model.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

	public Category findByName(String name);

	Optional<Category> findFirstByName(String name);



	@Query("Select c from Category c Where c.name=:name And c.parentCategory.name=:parentCategoryName")
	public Category findByNameAndParent(@Param("name") String name, 
			@Param("parentCategoryName")String parentCategoryName);

	@Query("SELECT c FROM Category c WHERE c.parentCategory = :parent")
	List<Category> findSubcategories(Category parent);

}

