package com.xxx.service;

import com.xxx.dao.UserDAO;
import com.xxx.dao.impl.UserDAOImpl;
import com.xxx.model.User;

public class UserService {

	private UserDAO userDAO;
	
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public void add(User u){
		this.userDAO.save(u);
	}
}
