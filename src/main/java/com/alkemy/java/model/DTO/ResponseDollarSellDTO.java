package com.alkemy.java.model.DTO;

public class ResponseDollarSellDTO {
	
	private float dollarSell;
	
	
	private float dollarValue;
	
	
	private float profitInPesos;


	public float getDollarSell() {
		return dollarSell;
	}


	public void setDollarSell(float dollarSell) {
		this.dollarSell = dollarSell;
	}


	public float getDollarValue() {
		return dollarValue;
	}


	public void setDollarValue(float dollarValue) {
		this.dollarValue = dollarValue;
	}


	public float getProfitInPesos() {
		return profitInPesos;
	}


	public void setProfitInPesos(float profitInPesos) {
		this.profitInPesos = profitInPesos;
	}


	public ResponseDollarSellDTO(float dollarSell, float dollarValue, float profitInPesos) {
		super();
		this.dollarSell = dollarSell;
		this.dollarValue = dollarValue;
		this.profitInPesos = profitInPesos;
	}


	public ResponseDollarSellDTO() {
		super();
	}
	
	

}
