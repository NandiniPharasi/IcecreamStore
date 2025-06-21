package com.demo.configuration;

import com.demo.service.JwtServiceImpl;
import com.demo.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	 public static String CURRENT_USER = "";

	    @Autowired
	    private JwtUtil jwtUtil;

	    @Autowired
	    private JwtServiceImpl jwtService;

	    @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

	        final String requestTokenHeader = request.getHeader("Authorization");

	        String username = null;
	        String jwtToken = null;
	        System.out.println(request.getRequestURI());
	        
	        System.out.println("requestTokenHeader"+requestTokenHeader);
	        
	        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
	            jwtToken = requestTokenHeader.substring(7);
	            try {
	                username = jwtUtil.getUsernameFromToken(jwtToken);
	                CURRENT_USER = username;
	                System.out.println("----CURRENT_USER:---"+CURRENT_USER);
	            } catch (IllegalArgumentException e) {
	                System.out.println("Unable to get JWT Token");
	            } catch (ExpiredJwtException e) {
	                System.out.println("JWT Token has expired");
	            }
	        } else {
	        	//CURRENT_USER="nan123";
	        	System.out.println("---Else:----"+CURRENT_USER);
	            System.out.println("JWT token does not start with Bearerrrrrrr");
	        }

	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

	            UserDetails userDetails = jwtService.loadUserByUsername(username);

	            if (jwtUtil.validateToken(jwtToken, userDetails)) {

	                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	            }
	        }
	        filterChain.doFilter(request, response);

	    }

}

