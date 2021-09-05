package com.alkemy.java.repo;

import com.alkemy.java.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepo extends JpaRepository<User, Integer> {
	
	User findByUsernameAndPassword(String name,String password);

	User findByUsername (String name);

}
