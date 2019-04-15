package com.bex.btca.model;

import java.util.HashMap;

public class EstadisticasRFQ {
	
	private String version;
	private String fechaOperativa;
	public EstadisticasRFQ(String version, String fechaOperativa) {
		super();
		this.version = version;
		this.fechaOperativa = fechaOperativa;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getFechaOperativa() {
		return fechaOperativa;
	}
	public void setFechaOperativa(String fechaOperativa) {
		this.fechaOperativa = fechaOperativa;
	}


	
	
	
}
