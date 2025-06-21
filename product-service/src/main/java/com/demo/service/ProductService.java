package com.demo.service;

import java.util.List;

import com.demo.entity.Product;

public interface ProductService {

	 public Product addNewProduct(Product product) ;
	 public List<Product> getAllProducts(int pageNumber, String searchKey);       
	 public void deleteProductDetails(Integer productId) ;
	 public Product updateProductDetails(Integer productId, Product pobj);
	 public List<Product> getProductDetails(boolean isSingleProductCheckout, Integer productId) ;
	
	//for feign 
	public Product getProductDetailsById(Integer productId) ;
}
