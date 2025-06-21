package com.demo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import com.dto.entity.ProductDto;


@FeignClient(name = "product-service", url = "http://localhost:9091")
public interface ProductServiceFeignClient {
	@GetMapping("product/getProductDetailsById/{productId}") 
    public ProductDto getProductDetailsById(@PathVariable("productId") Integer productId);
}
