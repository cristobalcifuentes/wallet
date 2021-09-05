package com.alkemy.java.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alkemy.java.model.User;

public interface ILoginRepo extends JpaRepository<User, Integer>{
	
	@Query("FROM User us where us.username = :user")
	User checkUserName(@Param("user") String user) throws Exception;
	
	@Transactional
	@Modifying
	@Query("UPDATE User us SET us.password = :password WHERE us.username = :name")
	void changePassword(@Param("password") String password, @Param("name") String name) throws Exception;

}
