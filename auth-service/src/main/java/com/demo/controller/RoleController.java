package com.demo.controller;

import com.demo.entity.Role;
import com.demo.service.RoleServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class RoleController {

	@Autowired
	private RoleServiceImpl roleService;

	@PostMapping({ "/createNewRole" })
	public Role createNewRole(@RequestBody Role role) {
		return roleService.createNewRole(role);
	}
}
