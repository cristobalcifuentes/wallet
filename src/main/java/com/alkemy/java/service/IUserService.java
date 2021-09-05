package com.alkemy.java.service;

import com.alkemy.java.model.User;

import java.util.List;

public interface IUserService {

    List<User> getAllUsers();

    User login(String name, String password);

    User save (User user);

    User findByUsername(String username);

}
