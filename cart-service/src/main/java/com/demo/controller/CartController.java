package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entity.Cart;
import com.dto.entity.CartDtoC;
import com.dto.entity.ProductDto;
import com.demo.service.AuthServiceFeignClient;
import com.demo.service.CartServiceImpl;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartServiceImpl cartService;

    @PreAuthorize("hasRole('User')")
    @GetMapping({"/addToCart/{productId}"})
    public CartDtoC addToCart(@PathVariable(name = "productId") Integer productId) {
        return cartService.addToCart(productId);
    }
 
    @PreAuthorize("hasRole('User')")
    @DeleteMapping({"/deleteCartItem/{cartId}"})
    public void deleteCartItem(@PathVariable(name = "cartId") Integer cartId) {
        cartService.deleteCartItem(cartId);
    }

    @PreAuthorize("hasRole('User')")
    @GetMapping({"/getCartDetails"})
    public List<CartDtoC> getCartDetails() {
        return cartService.getCartDetails();
    }
    
    //for feign
    @GetMapping("/getCartbyusername/{username}")
    public List<Cart> getCartbyusername(@PathVariable("username") String username){
    	return cartService.getCartbyusername(username);
    }
    
//    @PreAuthorize("hasRole('User')")
    @DeleteMapping("/deleteCartDetails/{cartId}")
    public void deleteCartDetails(@PathVariable("cartId") Integer cartId) {
    	cartService.deleteCartDetails(cartId);
    }
}