package com.dto.entity;



public class CartDtoC {
	
	private Integer cartId;
	private ProductDto product;
	private UserDto user;
	public Integer getCartId() {
		return cartId;
	}
	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}
	public ProductDto getProduct() {
		return product;
	}
	public void setProduct(ProductDto product) {
		this.product = product;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public CartDtoC(Integer cartId, ProductDto product, UserDto user) {
		super();
		this.cartId = cartId;
		this.product = product;
		this.user = user;
	}
	
	
}
