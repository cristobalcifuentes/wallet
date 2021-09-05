package com.alkemy.java.service;

import org.springframework.data.repository.query.Param;

import com.alkemy.java.model.Transaction;
import com.alkemy.java.model.Wallet;

import java.util.List;

public interface IWalletService {
	
	Wallet deposit(Double money,String detail,Integer userId) throws Exception;
	Wallet findWalletByUserId(@Param("userId") Integer userId) throws Exception;
	void updateWalletBalance(@Param("newBalance") Double newBalance) throws Exception;
	List<Wallet> transferMoneyUsertoUser(Double money, Integer userId, String name2, String detail) throws Exception;
	Double getWalletBalance(@Param("userId") Integer userId) throws Exception;
	
//	 Wallet expense(Transaction transaction) throws Exception;
	 Wallet expense(Integer userId, Double money, String detail) throws Exception;

}
