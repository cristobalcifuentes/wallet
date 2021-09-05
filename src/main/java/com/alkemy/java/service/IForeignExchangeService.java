package com.alkemy.java.service;


import com.alkemy.java.model.DTO.DolarOficialDTO;
import com.alkemy.java.model.DTO.ForeignExchangeDTO;
import com.alkemy.java.model.DTO.ResponseDollarSellDTO;
import com.alkemy.java.model.DTO.ResponseDollarsBoughtDTO;
import com.alkemy.java.model.DTO.TransactionForeignExchangeDTO;

public interface IForeignExchangeService {
	
	public ForeignExchangeDTO getForeignExchangeDTO(int idUser);
	
	public ResponseDollarsBoughtDTO transferWalletToForeingExchangeForPesos(TransactionForeignExchangeDTO foreing) throws Exception;
	
	public ResponseDollarsBoughtDTO transferWalletToForeingExchangeForDollars(TransactionForeignExchangeDTO foreing) throws Exception;
	
	public ResponseDollarSellDTO transferForeingExchangeToWalletForDollars(TransactionForeignExchangeDTO foreing) throws Exception;
	
	public ResponseDollarSellDTO transferForeingExchangeToWalletForPesos(TransactionForeignExchangeDTO foreing) throws Exception;
	
	

}
