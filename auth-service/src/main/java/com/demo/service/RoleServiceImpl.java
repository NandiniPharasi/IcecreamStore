package com.demo.service;

import com.demo.dao.RoleDao;
import com.demo.entity.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleDao roleDao;

	public Role createNewRole(Role role) {
		return roleDao.save(role);
	}
}
