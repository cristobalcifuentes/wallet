package com.alkemy.java.service;

import com.alkemy.java.model.ResetToken;

public interface IResetTokenService {
	
	ResetToken findByToken(String token);
	
	void save(ResetToken token);
	
	void delete(ResetToken token);

}
