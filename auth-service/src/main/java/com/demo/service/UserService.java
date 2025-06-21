package com.demo.service;

import java.util.HashSet;
import java.util.Set;

import com.demo.entity.Role;
import com.demo.entity.User;

public interface UserService {
	public void initRoleAndUser() ;

	public User registerNewUser(User user);

	public String getEncodedPassword(String password) ;
	
	//for feign 
    public User getUserDetailsById(String userName) ;
}

