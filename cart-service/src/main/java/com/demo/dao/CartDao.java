package com.demo.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.entity.Cart;
//import com.demo.entity.User;

@Repository
public interface CartDao extends CrudRepository<Cart, Integer > {
//    public List<Cart> findByUser(UserDto user);
	public List<Cart> findByUserName(String username);
}
