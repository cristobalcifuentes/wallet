package com.alkemy.java.repo;

import com.alkemy.java.model.Wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IWalletRepo extends JpaRepository<Wallet,Integer> {
	
	@Query("SELECT w from Wallet w WHERE w.user.userId = :userId")
	Wallet findWalletByUserId(@Param("userId") Integer userId) throws Exception;
	
	@Transactional
	@Modifying
	@Query("UPDATE Wallet wa SET wa.balance =:newBalance")
	void updateWalletBalance(@Param("newBalance") Double newBalance) throws Exception;
	
	@Query(value = "SELECT balance from wallet where user_id = :userId", nativeQuery = true)
	Double getWalletBalance(@Param("userId") Integer userId) throws Exception;
	
}	

