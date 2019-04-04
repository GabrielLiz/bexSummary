package com.bex.btca.model;

public class Totales {
	private String id_trans;
	private String version;
	private String status;
	private String isin;
	private String sent;
	private String trade_type;
	private String fecha_operativa;
	private String assset_class;
	
	
	
	public Totales() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Totales(String id_trans, String version, String status, String isin, String sent, String trade_type,
			String fecha_operativa, String assset_class) {
		super();
		this.id_trans = id_trans;
		this.version = version;
		this.status = status;
		this.isin = isin;
		this.sent = sent;
		this.trade_type = trade_type;
		this.fecha_operativa = fecha_operativa;
		this.assset_class = assset_class;
	}



	public String getId_trans() {
		return id_trans;
	}



	public void setId_trans(String id_trans) {
		this.id_trans = id_trans;
	}



	public String getVersion() {
		return version;
	}



	public void setVersion(String version) {
		this.version = version;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getIsin() {
		return isin;
	}



	public void setIsin(String isin) {
		this.isin = isin;
	}



	public String getSent() {
		return sent;
	}



	public void setSent(String sent) {
		this.sent = sent;
	}



	public String getTrade_type() {
		return trade_type;
	}



	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}



	public String getFecha_operativa() {
		return fecha_operativa;
	}



	public void setFecha_operativa(String fecha_operativa) {
		this.fecha_operativa = fecha_operativa;
	}



	public String getAssset_class() {
		return assset_class;
	}



	public void setAssset_class(String assset_class) {
		this.assset_class = assset_class;
	}



	@Override
	public String toString() {
		return "Totales [id_trans=" + id_trans + ", version=" + version + ", status=" + status + ", isin=" + isin
				+ ", sent=" + sent + ", trade_type=" + trade_type + ", fecha_operativa=" + fecha_operativa
				+ ", assset_class=" + assset_class + "]";
	}
	

	
	
	
	
}
