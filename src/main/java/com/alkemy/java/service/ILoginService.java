package com.alkemy.java.service;

import org.springframework.data.repository.query.Param;

import com.alkemy.java.model.User;

public interface ILoginService {
	
	User checkUserName(@Param("user") String user) throws Exception;
	boolean changePassword(String password, String name) throws Exception;

}
