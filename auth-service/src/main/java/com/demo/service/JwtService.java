package com.demo.service;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.demo.entity.JwtRequest;
import com.demo.entity.JwtResponse;
import com.demo.entity.User;

public interface JwtService {
	public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception ;
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException ;
//	Set getAuthority(User user);
//	void authenticate(String userName, String userPassword) throws Exception;
}
