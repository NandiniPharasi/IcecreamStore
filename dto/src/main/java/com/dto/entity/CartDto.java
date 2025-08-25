package com.dto.entity;


public class CartDto {
    private Integer cartId;
	
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
	
	public CartDto(Integer productId, String userName) {
		super();
		this.productId = productId;
		this.userName = userName;
	}
	
	public CartDto() {
	}
	
	
	   
}
