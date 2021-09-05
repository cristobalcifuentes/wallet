package com.alkemy.java.model.DTO;

public class ResponseDollarsBoughtDTO {
	
	private float dollarsBough;
	
	private float dollarValue;
	
	private float costInPesos;

	public float getDollarsBough() {
		return dollarsBough;
	}

	public void setDollarsBough(float dollarsBough) {
		this.dollarsBough = dollarsBough;
	}

	public float getDollarValue() {
		return dollarValue;
	}

	public void setDollarValue(float dollarValue) {
		this.dollarValue = dollarValue;
	}

	public float getCostInPesos() {
		return costInPesos;
	}

	public void setCostInPesos(float costInPesos) {
		this.costInPesos = costInPesos;
	}

	public ResponseDollarsBoughtDTO(float dollarsBough, float dollarValue, float costInPesos) {
		super();
		this.dollarsBough = dollarsBough;
		this.dollarValue = dollarValue;
		this.costInPesos = costInPesos;
	}

	public ResponseDollarsBoughtDTO() {
		super();
	}


	
	
	
	
	

}
