package com.demo.test;



import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.demo.dao.ProductDao;
import com.demo.entity.Product;
import com.demo.service.ProductServiceImpl;


@SpringBootTest
public class ProductServiceTest {
		
	@Autowired
	private ProductServiceImpl productservice;

	@Test
	public void testgetProductDetailsById() {
		Product p = productservice.getProductDetailsById(3);
		System.out.println(p);
		assertEquals(45000.0,p.getProductDiscountedPrice());
	}

}
