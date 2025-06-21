package com.demo.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.entity.OrderDetail;

@Repository
public interface OrderDetailDao extends CrudRepository<OrderDetail, Integer> {
//    public List<OrderDetail> findByUser(UserDto user);
	
	public List<OrderDetail> findByUserName(String username);
    public List<OrderDetail> findByOrderStatus(String status);
}
