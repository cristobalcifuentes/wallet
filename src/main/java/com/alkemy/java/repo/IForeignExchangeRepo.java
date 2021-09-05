package com.alkemy.java.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alkemy.java.model.ForeignExchange;

public interface IForeignExchangeRepo extends JpaRepository<ForeignExchange, Integer> {
	
	
	@Query(value = "SELECT COUNT(*) FROM foreign_exchange where id_user=?", nativeQuery = true)
	int exist(int id_user);
	
	@Query(value = "SELECT * FROM foreign_exchange where id_user=?", nativeQuery = true)
	ForeignExchange getForeignExchange(int id_user);

}
