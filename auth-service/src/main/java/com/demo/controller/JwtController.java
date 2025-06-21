package com.demo.controller;

import com.demo.configuration.JwtRequestFilter;
import com.demo.dto.UsernameAuthoritiesDto;
import com.demo.entity.JwtRequest;
import com.demo.entity.JwtResponse;
import com.demo.entity.User;
import com.demo.service.JwtServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class JwtController {

	@Autowired
	private JwtServiceImpl jwtService;

	@PostMapping({ "/authenticate" })
	public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
		return jwtService.createJwtToken(jwtRequest);
	}
	
	//for feign
	@Autowired
	private JwtRequestFilter jwtrequestfilter;
	
	@GetMapping("/jwtgetcurrentuser")
	public String jwtgetcurrentuser() {
		System.out.println("current user: "+jwtrequestfilter.CURRENT_USER);
		return jwtrequestfilter.CURRENT_USER;
	}
	
	@PostMapping(value = "/check_token", produces = APPLICATION_JSON_VALUE)
    UsernameAuthoritiesDto checkToken(@RequestParam(value="token") String token){
		org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UsernameAuthoritiesDto usernameAuthoritiesDto = new UsernameAuthoritiesDto();
        usernameAuthoritiesDto.setUsername(principal.getUsername());
        Set<String> authorities = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        usernameAuthoritiesDto.setAuthorities(authorities);
        return usernameAuthoritiesDto;
    }
	
}
