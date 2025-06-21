package com.demo.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.dto.entity.CartDto;

@FeignClient(name = "cart-service", url = "http://localhost:9092")
public interface CartServiceFeignClient {
	
	@GetMapping("/cart/getCartbyusername/{username}")
	public List<CartDto> getCartbyusername(@PathVariable("username") String username);
	
	@DeleteMapping("/cart/deleteCartDetails/{cartId}")
    public void deleteCartDetails(@PathVariable("cartId") Integer cartId);
}
