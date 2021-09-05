package com.alkemy.java.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.java.model.DTO.ForeignExchangeDTO;
import com.alkemy.java.model.DTO.ResponseDollarSellDTO;
import com.alkemy.java.model.DTO.ResponseDollarsBoughtDTO;
import com.alkemy.java.model.DTO.TransactionForeignExchangeDTO;
import com.alkemy.java.service.IForeignExchangeService;

@RestController
@RequestMapping("foreign-exchange")
public class ForeignExchangeController {
	
	@Autowired
	private IForeignExchangeService exchangeService;
	
	
	@GetMapping
	public ResponseEntity<ForeignExchangeDTO> getForeignExchange( Authentication authentication){
		
		ForeignExchangeDTO dto = exchangeService.getForeignExchangeDTO(Integer.valueOf(authentication.getName()));
		
		return new ResponseEntity<ForeignExchangeDTO>(dto, HttpStatus.OK);
	}
	
	@PostMapping("/buy-for-pesos")
	public ResponseEntity<ResponseDollarsBoughtDTO> buyDollarsForPesos (@RequestParam float pesos, Authentication authentication) throws Exception{
		
		TransactionForeignExchangeDTO foreing = new TransactionForeignExchangeDTO(Integer.valueOf(authentication.getName()),  pesos);
		
		ResponseDollarsBoughtDTO dollarsBough = exchangeService.transferWalletToForeingExchangeForPesos(foreing);
		
		return new ResponseEntity<ResponseDollarsBoughtDTO>(dollarsBough, HttpStatus.OK);
	}
	
	
	@PostMapping("/buy-for-dollar")
	public ResponseEntity<ResponseDollarsBoughtDTO> buyDollarsFordollar (@RequestParam float dollars, Authentication authentication) throws Exception{
		
		TransactionForeignExchangeDTO foreing = new TransactionForeignExchangeDTO(Integer.valueOf(authentication.getName()),  dollars );
		
		ResponseDollarsBoughtDTO dollarsBough = exchangeService.transferWalletToForeingExchangeForDollars(foreing);
		
		return new ResponseEntity<ResponseDollarsBoughtDTO>(dollarsBough, HttpStatus.OK);
	}
	
	
	
	@PostMapping("/sell-for-dollar")
	public ResponseEntity<ResponseDollarSellDTO> sellDollarsForDollar (@RequestParam float dollars, Authentication authentication) throws Exception{
		
		TransactionForeignExchangeDTO foreing = new TransactionForeignExchangeDTO(Integer.valueOf(authentication.getName()), dollars);
		
		ResponseDollarSellDTO DollarSell = exchangeService.transferForeingExchangeToWalletForDollars(foreing);
		
		return new ResponseEntity<ResponseDollarSellDTO>(DollarSell, HttpStatus.OK);
	}
	
	@PostMapping("/sell-for-pesos")
	public ResponseEntity<ResponseDollarSellDTO> sellDollarsForPesos (@RequestParam float pesos, Authentication authentication) throws Exception{
		
		TransactionForeignExchangeDTO foreing = new TransactionForeignExchangeDTO(Integer.valueOf(authentication.getName()), pesos);
		
		ResponseDollarSellDTO DollarSell = exchangeService.transferForeingExchangeToWalletForPesos(foreing);
		
		return new ResponseEntity<ResponseDollarSellDTO>(DollarSell, HttpStatus.OK);
	}
	

}
