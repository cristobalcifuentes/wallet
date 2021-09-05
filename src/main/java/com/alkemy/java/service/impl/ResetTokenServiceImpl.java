package com.alkemy.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alkemy.java.model.ResetToken;
import com.alkemy.java.repo.IResetTokenRepo;
import com.alkemy.java.service.IResetTokenService;

@Service
public class ResetTokenServiceImpl implements IResetTokenService {

	@Autowired
	private IResetTokenRepo resetTokenRepo;
	
	@Override
	public ResetToken findByToken(String token) {
		
		return resetTokenRepo.findByToken(token);
	}

	@Override
	public void save(ResetToken token) {
		
		resetTokenRepo.save(token);
		
	}

	@Override
	public void delete(ResetToken token) {
		
		resetTokenRepo.delete(token);
		
	}

}
