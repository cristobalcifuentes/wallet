package com.alkemy.java.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alkemy.java.exception.BadRequestException;
import com.alkemy.java.model.TermDeposit;
import com.alkemy.java.model.Transaction;
import com.alkemy.java.model.TransactionType;
import com.alkemy.java.model.User;
import com.alkemy.java.model.Wallet;
import com.alkemy.java.repo.ITermDepositRepo;
import com.alkemy.java.repo.ITransactionRepo;
import com.alkemy.java.repo.IWalletRepo;
import com.alkemy.java.service.ITermDepositService;

@Service
public class TermDepositServiceImpl implements ITermDepositService{

	private final static Double DAILY_INTEREST_RATE= 0.01;
	private final static String STATUS = "Ok";
	private final static String TAKE_DETAIL = "Toma PF";
	private final static String WITHDRAW_DETAIL = "Retiro PF";
	
	@Autowired
	private ITermDepositRepo termDepositRepo;
	@Autowired
	private IWalletRepo walletRepo;
	@Autowired
	private ITransactionRepo transactionRepo;
	
	@Override
	public TermDeposit takeTermDeposit(TermDeposit termDeposit) throws Exception {
		
		User user = termDeposit.getUser();
		Integer userId = termDeposit.getUser().getUserId();
		Wallet wallet = walletRepo.findWalletByUserId(userId);
		Double depositAmount = termDeposit.getDepositAmount();
		Double currentWalletBalance = wallet.getBalance();
		
		if(depositAmount > currentWalletBalance || depositAmount < 1) {
			throw new BadRequestException("Monto ingresado es incorrecto, no es posible tomar el depósito plazo fijo");
		}
		wallet.setBalance(currentWalletBalance - depositAmount);
		Transaction newTransaction = new Transaction(LocalDateTime.now(), depositAmount, TAKE_DETAIL, STATUS, user , TransactionType.DEPOSITO_PLAZO_FIJO);
		transactionRepo.save(newTransaction);

		return termDepositRepo.save(termDeposit);
	}

	@Override
	public boolean withdrawDeposit(Integer id) throws Exception {
		
		Optional<TermDeposit> td = termDepositRepo.findById(id);
		User user = td.get().getUser();
		Integer userId = td.get().getUser().getUserId();
		Wallet wallet = walletRepo.findWalletByUserId(userId);
		Double currentWalletBalance = wallet.getBalance();
		
		if(td.isPresent() && td.get().getActive()) {
			
			LocalDateTime withdrawalDate = LocalDateTime.now();
			
			//Syso and calculation period per second is only to simulate difference in DEMO.
			Integer difference = Duration.between(td.get().getStartDate(), withdrawalDate).toSecondsPart();		
			System.out.println(difference);
			Double withdrawingAmount = (td.get().getDepositAmount()) * (1 + ((DAILY_INTEREST_RATE) * (difference)));
			Double newWalletBalance = currentWalletBalance + withdrawingAmount;
			td.get().setWithdrawalDate(LocalDateTime.now());
			wallet.setBalance(newWalletBalance);
			transactionRepo.save(new Transaction(withdrawalDate, withdrawingAmount, WITHDRAW_DETAIL, STATUS, user, TransactionType.RETIRO_PLAZO_FIJO));
			termDepositRepo.deleteById(id);
			return true;
			
		} throw new BadRequestException ("Ha ocurrido un error, inténtelo de nuevo por favor");
		
	}

}
