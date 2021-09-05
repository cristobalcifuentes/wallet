package com.alkemy.java.model.DTO;

public class TransactionForeignExchangeDTO {
	
	private int idUser;
	
	
	private float amount;
	

	public int getIdUser() {
		return idUser;
	}


	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}


	public float getAmount() {
		return amount;
	}


	public void setAmount(float pesos) {
		this.amount = pesos;
	}
	
	

	@Override
	public String toString() {
		return "TransaccionForeingDTO [idUser=" + idUser + " , pesos="
				+ amount + "]";
	}


	public TransactionForeignExchangeDTO(int idUser, float amount) {
		this.idUser = idUser;
		this.amount = amount;
	}


	public TransactionForeignExchangeDTO() {
	}
	
	
	
	
	

}
