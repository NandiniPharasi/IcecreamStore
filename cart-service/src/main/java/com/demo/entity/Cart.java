package com.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import com.demo.service.ProductServiceFeignClient;

@Entity
@Table
public class Cart {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cartId;
	
//    @OneToOne
//    private Product product;
//    
//    @OneToOne
//    private User user;
	
	private Integer productId;
	private String userName;
	public Integer getCartId() {
		return cartId;
	}
	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Cart(Integer productId, String userName) {
		super();
		this.productId = productId;
		this.userName = userName;
	}
	
	public Cart() {
	}
	
	
	   
}
