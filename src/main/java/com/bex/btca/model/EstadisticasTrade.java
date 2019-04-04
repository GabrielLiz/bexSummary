package com.bex.btca.model;

public class EstadisticasTrade {

	private String busqueda;
	private int valores;
	public EstadisticasTrade(String busqueda, int valores) {
		super();
		this.busqueda = busqueda;
		this.valores = valores;
	}
	public EstadisticasTrade() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getBusqueda() {
		return busqueda;
	}
	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}
	public int getValores() {
		return valores;
	}
	public void setValores(int valores) {
		this.valores = valores;
	}
	@Override
	public String toString() {
		return "EstadisticasTrade [busqueda=" + busqueda + ", valores=" + valores + "]";
	}
	
	
}
