package com.alkemy.java.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alkemy.java.model.ResetToken;

public interface IResetTokenRepo extends JpaRepository<ResetToken, Integer>{
	
	ResetToken findByToken(String token);

}
