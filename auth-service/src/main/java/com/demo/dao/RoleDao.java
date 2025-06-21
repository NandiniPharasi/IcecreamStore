package com.demo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.entity.Role;

@Repository
public interface RoleDao extends CrudRepository<Role, String> {

}
