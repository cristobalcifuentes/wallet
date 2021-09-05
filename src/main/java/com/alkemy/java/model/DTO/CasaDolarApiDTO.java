package com.alkemy.java.model.DTO;

public class CasaDolarApiDTO {
		
	
	private String compra;
	
	
	private String venta;
	
	
	private String agencia;
	
	
	private String nombre;
	
	
	private String variacion;
	
	
	private String ventaCero;
	
	
	private String decimales;


	public String getCompra() {
		return compra;
	}


	public void setCompra(String compra) {
		this.compra = compra;
	}


	public String getVenta() {
		return venta;
	}


	public void setVenta(String venta) {
		this.venta = venta;
	}


	public String getAgencia() {
		return agencia;
	}


	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getVariacion() {
		return variacion;
	}


	public void setVariacion(String variacion) {
		this.variacion = variacion;
	}


	public String isVentaCero() {
		return ventaCero;
	}


	public void setVentaCero(String ventaCero) {
		this.ventaCero = ventaCero;
	}


	public String getDecimales() {
		return decimales;
	}


	public void setDecimales(String decimales) {
		this.decimales = decimales;
	}


	@Override
	public String toString() {
		return "CasaDolarApiDTO [compra=" + compra + ", venta=" + venta + ", agencia=" + agencia + ", nombre=" + nombre
				+ ", variacion=" + variacion + ", ventaCero=" + ventaCero + ", decimales=" + decimales + "]";
	}
	
	
	
	
	
	
	



}
