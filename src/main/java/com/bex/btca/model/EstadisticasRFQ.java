package com.bex.btca.model;

import java.util.HashMap;

public class EstadisticasRFQ {
	
	private String version;

	public EstadisticasRFQ(String version) {
		super();
		this.version = version;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "EstadisticasRFQ [version=" + version + "]";
	}
	
	
	
	
}
