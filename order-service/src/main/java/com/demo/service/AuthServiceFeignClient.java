package com.demo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



import com.dto.entity.UserDto;

@FeignClient(name = "auth-service", url = "http://localhost:9090")
public interface AuthServiceFeignClient {
	@GetMapping("/auth/getUserDetailsById/{userName}")
    public UserDto getUserDetailsById(@PathVariable("userName") String userName);
	
	@GetMapping("/auth/jwtgetcurrentuser")
	public String jwtgetcurrentuser();
}

