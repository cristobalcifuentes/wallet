package com.alkemy.java.model.DTO;

public class DolarOficialDTO {
	
	private float purchasePrice;
	
	
	private float salePrice;
	

	public void build (DolarApiDTO dolarApiDTO) {

		this.purchasePrice = Float.parseFloat(dolarApiDTO.getCasa().getCompra().replaceAll(",", "."));
		this.salePrice = Float.parseFloat(dolarApiDTO.getCasa().getVenta().replaceAll(",", "."));
	}


	public float getPurchasePrice() {
		return purchasePrice;
	}


	public void setPurchasePrice(float precioCompra) {
		this.purchasePrice = precioCompra;
	}


	public float getSalePrice() {
		return salePrice;
	}


	public void setSalePrice(float precioVenta) {
		this.salePrice = precioVenta;
	}


	@Override
	public String toString() {
		return "DolarOficialDTO [purchasePrice=" + purchasePrice + ", salePrice=" + salePrice + "]";
	}
	
	
	

}
