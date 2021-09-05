package com.alkemy.java.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alkemy.java.model.TermDeposit;

public interface ITermDepositRepo extends JpaRepository<TermDeposit, Integer> {

}
