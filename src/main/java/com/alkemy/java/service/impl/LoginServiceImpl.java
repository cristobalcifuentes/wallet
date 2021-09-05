package com.alkemy.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alkemy.java.model.User;
import com.alkemy.java.repo.ILoginRepo;
import com.alkemy.java.service.ILoginService;

@Service
public class LoginServiceImpl implements ILoginService{
	
	@Autowired
	private ILoginRepo loginRepo;

	@Override
	public User checkUserName(String user) throws Exception {
		
		User us = null;
		try {
			us = loginRepo.checkUserName(user);
			us = us != null ? us : new User();
		}catch (Exception e) {
			us = new User();
		}
		return us;
	}

	@Override
	public boolean changePassword(String password, String name) throws Exception {
		
		boolean response = false;
		try {
			loginRepo.changePassword(password, name);
			response = true;
		}catch (Exception e) {
			response = false;
		}
		return response;
	}

}
