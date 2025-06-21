package com.demo.service;

import com.demo.dao.RoleDao;
import com.demo.dao.UserDao;
import com.demo.entity.Role;
import com.demo.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements  UserService{

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public void initRoleAndUser() {

		Role adminRole = new Role();
		adminRole.setRoleName("Admin");
		adminRole.setRoleDescription("Admin role");
		roleDao.save(adminRole);

		Role userRole = new Role();
		userRole.setRoleName("User");
		userRole.setRoleDescription("Default role for newly created record");
		roleDao.save(userRole);

		User adminUser = new User();
		adminUser.setUserName("admin1");
		adminUser.setUserPassword(getEncodedPassword("admin@1"));
		adminUser.setUserFirstName("admin");
		adminUser.setUserLastName("admin");
		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(adminRole);
		adminUser.setRole(adminRoles);
		userDao.save(adminUser);

        User user = new User();
        user.setUserName("nan123");
        user.setUserPassword(getEncodedPassword("nan@123"));
        user.setUserFirstName("Nandini");
        user.setUserLastName("Pharasi");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRole(userRoles);
        userDao.save(user);
	}

	public User registerNewUser(User user) {
		Role role = roleDao.findById("User").get();
		Set<Role> userRoles = new HashSet<>();
		userRoles.add(role);
		user.setRole(userRoles);
		user.setUserPassword(getEncodedPassword(user.getUserPassword()));

		return userDao.save(user);
	}

	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}
	
	//for feign 
    public User getUserDetailsById(String userName) {
		User p = userDao.findByUserName(userName);
		return p;
	}
}
