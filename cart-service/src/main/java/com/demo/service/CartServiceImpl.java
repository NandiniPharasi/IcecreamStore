package com.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

//import com.demo.configuration.JwtRequestFilter;
//import com.demo.configuration.JwtRequestFilter;
import com.demo.dao.CartDao;
//import com.demo.dao.ProductDao;
//import com.demo.dao.UserDao;
import com.demo.entity.Cart;
import com.dto.entity.CartDtoC;
import com.dto.entity.ProductDto;
import com.dto.entity.UserDto;
//import com.demo.entity.Product;
//import com.demo.entity.User;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
    private CartDao cartDao;

//    @Autowired
//    private ProductDao productDao;
//
//    @Autowired
//    private UserDao userDao;
	
	@Autowired
	private AuthServiceFeignClient authservicefeignclient;
	
	@Autowired
	private ProductServiceFeignClient productservicefeignclient;

	

    public CartDtoC addToCart(Integer productId) {
      
    	ProductDto product = productservicefeignclient.getProductDetailsById(productId);

    	//String username = authservicefeignclient.jwtgetcurrentuser();
//    	String username = JwtRequestFilter.CURRENT_USER;
    	String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserDto user = null;
        if(username != null) {
            user = authservicefeignclient.getUserDetailsById(username);
        }
        
//        List<Cart> cartList = cartDao.findByUser(user);
//        List<Cart> filteredList = cartList.stream().filter(x -> x.getProduct().getProductId() == productId).collect(Collectors.toList());
//
//        if(filteredList.size() > 0) {
//            return null;
//        }
        
        List<Cart> cartList = cartDao.findByUserName(username);
        List<Cart> filteredList = cartList.stream().filter(x -> x.getProductId() == productId).collect(Collectors.toList());

        if(filteredList.size() > 0) {
            return null;
        }
        
        if(product != null && user != null) {
            Cart cart = new Cart(product.getProductId(), user.getUserName());
            cartDao.save(cart);
            CartDtoC cartdto = new CartDtoC(cart.getCartId(),product,user);
            return cartdto;
        }
        System.out.println("Product obj: "+product.getProductName());
        System.out.println("User obj: "+user);
        
        return null;
    }
    
    
    	public void deleteCartItem(Integer cartId) {
    		cartDao.deleteById(cartId);
    	}

    	public List<CartDtoC> getCartDetails() {
//    		String username = authservicefeignclient.jwtgetcurrentuser();
//    		String username = JwtRequestFilter.CURRENT_USER;
    		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		UserDto user = authservicefeignclient.getUserDetailsById(username);
    		List<CartDtoC> cartDtoList = new ArrayList<>();
    		List<Cart> cartList = cartDao.findByUserName(username);
    		for(Cart i: cartList) {
    			ProductDto product = productservicefeignclient.getProductDetailsById(i.getProductId());
    			CartDtoC obj = new CartDtoC(i.getCartId(),product,user);
    			cartDtoList.add(obj);
    		}
    		return cartDtoList;
//    		return cartDao.findByUser(user);
    		
    }
    	
    	
    //for feign
    public List<Cart> getCartbyusername(String username){
    	return cartDao.findByUserName(username);
    }
    public void deleteCartDetails(Integer cartId) {
		cartDao.deleteById(cartId);
	}
}
