package com.alkemy.java.service;

import com.alkemy.java.model.TermDeposit;

public interface ITermDepositService {
	
	TermDeposit takeTermDeposit (TermDeposit termDeposit) throws Exception;
	boolean withdrawDeposit (Integer id) throws Exception;

}
