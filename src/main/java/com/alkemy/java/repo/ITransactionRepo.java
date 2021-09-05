package com.alkemy.java.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.alkemy.java.model.Transaction;

public interface ITransactionRepo extends JpaRepository<Transaction, Integer>{


	@Transactional
	@Modifying
	@Query ("UPDATE Transaction tr SET tr.detail =:newDetail WHERE tr.transactionId =:transactionId")
	void updateTransactionDetail(@Param("transactionId") Integer transactionId, @Param("newDetail") String newDetail ) throws Exception;

	@Query("SELECT t from Transaction t WHERE t.user.userId = :userId")
	List<Transaction> transactionListByUserId(@Param("userId") Integer userId);
}
