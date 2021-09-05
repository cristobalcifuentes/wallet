package com.alkemy.java.model.DTO;

import com.alkemy.java.model.ForeignExchange;

public class ForeignExchangeDTO {
	
	private int idUsuario;
	
	
	private double balance;
	
	
	public ForeignExchangeDTO() {
		super();
	}


	public ForeignExchangeDTO(int idUsuario, double balance) {
		super();
		this.idUsuario = idUsuario;
		this.balance = balance;
	}


	public int getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}


	public double getBalance() {
		return balance;
	}


	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public void build (ForeignExchange exchange) {
		
		 this.idUsuario = exchange.getUser().getUserId();
		
		this.balance = exchange.getBalance();
		
	}


	@Override
	public String toString() {
		return "ForeignExchangeDTO [idUsuario=" + idUsuario + ", balance=" + balance + "]";
	}
	
	
	
	
	

}
