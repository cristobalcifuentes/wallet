package com.alkemy.java.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alkemy.java.exception.BadRequestException;
import com.alkemy.java.model.Transaction;
import com.alkemy.java.repo.ITransactionRepo;
import com.alkemy.java.service.ITransactionService;

@Service
public class TransactionServiceImpl implements ITransactionService{
	
	@Autowired
	private ITransactionRepo transactionRepo;

	@Override
	public Transaction resgisterTransaction(Transaction transaction) {
		
		return transactionRepo.save(transaction);
	}

	@Override
	public List<Transaction> listAll() {
		
		return transactionRepo.findAll();
	}

	@Override
	public Transaction findById(Integer transactionId) {
		Optional<Transaction> op = transactionRepo.findById(transactionId);
		return op.isPresent() ? op.get() : new Transaction();
	}

	@Override
	public Transaction updateTransactionDetail(Integer transactionId, String newDetail) throws Exception {
		Optional<Transaction> op = transactionRepo.findById(transactionId);
	    if( !op.isPresent()) {
	    	throw new BadRequestException("El ID:" + transactionId + " NO existe");
	    }
	    op.get().setDetail(newDetail);
	    return transactionRepo.save(op.get());
	}

	@Override
	public Page<Transaction> pageableTransactionList(Pageable pageable, Integer userId) {
		
		pageable = (Pageable) transactionRepo.transactionListByUserId(userId);
		return transactionRepo.findAll(pageable);
	
	}

	@Override
	public List<Transaction> transactionListByUserId(Integer userId) {
		
		return transactionRepo.transactionListByUserId(userId);
	}

}
