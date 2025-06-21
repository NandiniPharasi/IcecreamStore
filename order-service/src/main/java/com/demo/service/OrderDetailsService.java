package com.demo.service;


import java.util.List;

import com.demo.entity.OrderInput;
import com.dto.entity.OrderDetailDto;

public interface OrderDetailsService {
	 	public List<OrderDetailDto> getAllOrderDetails(String status) ;
	    public List<OrderDetailDto> getOrderDetails() ;
	    public void placeOrder(OrderInput orderInput, boolean isSingleProductCheckout) ;
	    public void markOrderAsDelivered(Integer orderId) ;

	}


