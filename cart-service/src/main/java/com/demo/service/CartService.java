package com.demo.service;

import java.util.List;

import com.demo.entity.Cart;
import com.dto.entity.CartDtoC;


public interface CartService {
	 public CartDtoC addToCart(Integer productId) ;
	 public void deleteCartItem(Integer cartId);
	 public List<CartDtoC> getCartDetails() ;
	    	
	    //for feign
	    public List<Cart> getCartbyusername(String username);
	    public void deleteCartDetails(Integer cartId) ;
	}


