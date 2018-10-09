package com.capgemini.productapp.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.productapp.entity.Product;
import com.capgemini.productapp.exception.ProductNotFoundException;
import com.capgemini.productapp.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	Logger LOGGER = Logger.getLogger(ProductController.class.getName());
	@PostMapping("/product")
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		ResponseEntity<Product> responseEntity = new ResponseEntity<Product>(productService.addProduct(product),
				HttpStatus.OK);

		return responseEntity;
	}

	@PutMapping("/product")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
		try {
			productService.findProductById(product.getProductId());
			return new ResponseEntity<Product>(productService.updateProduct(product), HttpStatus.OK);
		} catch (ProductNotFoundException exception) {
			LOGGER.info("NO PRODUCT FOUND FOR UPDATION....NOT FOUND"+"RETURNED HTTP STATUS CODE 404");
		}
		return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/products/{productId}")
	public ResponseEntity<Product> findProductById(@PathVariable int productId) {
		try {
			Product productFromDb = productService.findProductById(productId);
			return new ResponseEntity<Product>(productFromDb, HttpStatus.OK);
		} catch (ProductNotFoundException exception) {
			LOGGER.info("PRODUCT NOT FOUND"+"RETURNED HTTP STATUS 404");
		}
		return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/product1/{productName}")
	public ResponseEntity<List> findProductByName(@PathVariable String productName)
	{
		List<Product> productFromDb = productService.findByName(productName);
		return new ResponseEntity<List>(productFromDb,HttpStatus.OK);
	}
	
	
	@GetMapping("/product/{productCategory}")
	public ResponseEntity<List> findProductByCategory(@PathVariable String productCategory){
		List<Product> productFromDb = productService.findByCategory(productCategory);
		return new ResponseEntity<List>(productFromDb,HttpStatus.OK);
	}

	@GetMapping("/product")
	public ResponseEntity<List> findProductByPrice(@RequestParam Double lowPrice,@RequestParam Double highPrice){
		List<Product> productFromDb = productService.findByPrice(lowPrice,highPrice);

		return new ResponseEntity<List>(productFromDb,HttpStatus.OK);
		
	}
	
	@GetMapping("/all")
	public ResponseEntity<List> findProductByCategoryAndPrice(@RequestParam String productCategory,@RequestParam Double lowPrice,@RequestParam Double highPrice)
	{
		List<Product> productFromDb = productService.findByCategotyAndPrice(productCategory, lowPrice, highPrice);
		return new ResponseEntity<List>(productFromDb,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/products/{productId}")
	public ResponseEntity<Product> deleteProduct(@PathVariable int productId) {
		try {
			Product productFromDb = productService.findProductById(productId);
			if (productFromDb != null) {
				productService.deleteProduct(productFromDb);
				return new ResponseEntity<Product>(HttpStatus.OK);
			}
		} catch (ProductNotFoundException exception) {
			// logged the exception
		}
		return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
	}

}
