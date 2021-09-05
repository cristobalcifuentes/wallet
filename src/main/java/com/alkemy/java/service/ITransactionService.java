package com.alkemy.java.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.alkemy.java.model.Transaction;

public interface ITransactionService {
	
	Transaction resgisterTransaction(Transaction transaction);
	List<Transaction> listAll();
	Transaction findById(Integer transactionId);
	
	Transaction updateTransactionDetail(@Param("transactionId") Integer transactionId, @Param("newDetail") String newDetail ) throws Exception;
	Page<Transaction> pageableTransactionList(Pageable pageable, Integer userId);
	List<Transaction> transactionListByUserId(@Param("userId") Integer userId);
	
}
