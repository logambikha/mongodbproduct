package com.capgemini.productapp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.productapp.entity.Product;
import com.capgemini.productapp.exception.ProductNotFoundException;
import com.capgemini.productapp.repository.ProductRepository;
import com.capgemini.productapp.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product addProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Product findProductById(int productId) throws ProductNotFoundException {
		Optional<Product> optionalProduct = productRepository.findById(productId);
		if(!optionalProduct.isPresent())
			
		throw new ProductNotFoundException("Product does not exists");
		return optionalProduct.get();
	}

	@Override
	public void deleteProduct(Product product) {
		productRepository.delete(product);
	}

	@Override
	public List<Product> findByCategory(String productCategory) {
		return productRepository.findOrderByProductCategory(productCategory);
	}

	@Override
	public List<Product> findByName(String productName) {
		return productRepository.findOrderByProductName(productName);
	}

	@Override
	public List<Product> findByPrice(Double lowPrice, Double highPrice) {
		return productRepository.findOrderByProductPriceBetween(lowPrice, highPrice);
	}

	@Override
	public List<Product> findByCategotyAndPrice(String productCategory, Double lowPrice, Double highPrice) {
		return productRepository.findOrderByProductCategoryAndPrice(productCategory, lowPrice, highPrice);
	}

	


}
