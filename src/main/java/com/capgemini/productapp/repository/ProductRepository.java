package com.capgemini.productapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.capgemini.productapp.entity.Product;

public interface ProductRepository extends MongoRepository<Product, Integer>{
	
	@Query("{'productName' : ?0}")
	List<Product> findOrderByProductName(String productName);
	
	@Query("{'productCategory' : ?0}")
	List<Product> findOrderByProductCategory(String productCategory);

	@Query("{'productPrice':{'$gt':?0 ,'$lt':?1}}")
	List<Product> findOrderByProductPriceBetween(Double lowPrice,Double highPrice);

	@Query("{'productCategory' : ?0},{'productPrice':{'$gt':?0 ,'$lt':?1}}")
	List<Product> findOrderByProductCategoryAndPrice(String productCategory,Double lowPrice,Double highPrice);
}
