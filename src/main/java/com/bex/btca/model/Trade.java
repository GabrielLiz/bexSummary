package com.bex.btca.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.style.ToStringCreator;

public class Trade {

	private String IDdetransacción;
	private String Version;
	private String Acción;
	private String Estatus;
	private String FeedProducer;
	private String Repetir;
	private String Razón;
	private String Descripcióndelvalor;
	private String ISIN;
	private String Enviado;
	private String Received;
	private String Tipodeoperación;
	private String TradeSubtype;
	private String TradingMode;
	private String TradingPlatform;
	private String TradingSystem;
	private String Capacity;
	private String Cantidad;
	private String Tipodecantidad;
	private String Precio;
	private String Divisadelprecio;
	private String PriceType;
	private String Centro;
	private String Fechadetransacción;
	private String AssetClass;
	private String OrderType;
	private String OrderAccountType;
	private String Costs;
	private String Rebates;
	private String LastCapacity;
	private String BestBid;
	private String BestOffer;
	private String IDdeejecutor;
	private String Tipodeejecutor;
	private String IDdecomprador;
	private String Tipodecomprador;
	private String IDdevendedor;
	private String Tipodevendedor;
	private String Últmensaje;
	private boolean procesado;
	
	public Trade() {
		
		
	}
	
	


	public boolean isProcesado() {
		return procesado;
	}




	public void setProcesado(boolean procesado) {
		this.procesado = procesado;
	}




	public Trade(String iDdetransacción, String version, String acción, String estatus, String feedProducer,
			String repetir, String razón, String descripcióndelvalor, String iSIN, String enviado, String received,
			String tipodeoperación, String tradeSubtype, String tradingMode, String tradingPlatform,
			String tradingSystem, String capacity, String cantidad, String tipodecantidad, String precio,
			String divisadelprecio, String priceType, String centro, String fechadetransacción, String assetClass,
			String orderType, String orderAccountType, String costs, String rebates, String lastCapacity,
			String bestBid, String bestOffer, String iDdeejecutor, String tipodeejecutor, String iDdecomprador,
			String tipodecomprador, String iDdevendedor, String tipodevendedor, String últmensaje) {
		super();
		IDdetransacción = iDdetransacción;
		Version = version;
		Acción = acción;
		Estatus = estatus;
		FeedProducer = feedProducer;
		Repetir = repetir;
		Razón = razón;
		Descripcióndelvalor = descripcióndelvalor;
		ISIN = iSIN;
		Enviado = enviado;
		Received = received;
		Tipodeoperación = tipodeoperación;
		TradeSubtype = tradeSubtype;
		TradingMode = tradingMode;
		TradingPlatform = tradingPlatform;
		TradingSystem = tradingSystem;
		Capacity = capacity;
		Cantidad = cantidad;
		Tipodecantidad = tipodecantidad;
		Precio = precio;
		Divisadelprecio = divisadelprecio;
		PriceType = priceType;
		Centro = centro;
		Fechadetransacción = fechadetransacción;
		AssetClass = assetClass;
		OrderType = orderType;
		OrderAccountType = orderAccountType;
		Costs = costs;
		Rebates = rebates;
		LastCapacity = lastCapacity;
		BestBid = bestBid;
		BestOffer = bestOffer;
		IDdeejecutor = iDdeejecutor;
		Tipodeejecutor = tipodeejecutor;
		IDdecomprador = iDdecomprador;
		Tipodecomprador = tipodecomprador;
		IDdevendedor = iDdevendedor;
		Tipodevendedor = tipodevendedor;
		Últmensaje = últmensaje;
	}






	public String getIDdetransacción() {
		return IDdetransacción;
	}



	public void setIDdetransacción(String iDdetransacción) {
		IDdetransacción = iDdetransacción;
	}



	public String getVersion() {
		return Version;
	}



	public void setVersion(String version) {
		Version = version;
	}



	public String getAcción() {
		return Acción;
	}



	public void setAcción(String acción) {
		Acción = acción;
	}



	public String getEstatus() {
		return Estatus;
	}



	public void setEstatus(String estatus) {
		Estatus = estatus;
	}



	public String getFeedProducer() {
		return FeedProducer;
	}



	public void setFeedProducer(String feedProducer) {
		FeedProducer = feedProducer;
	}



	public String getRepetir() {
		return Repetir;
	}



	public void setRepetir(String repetir) {
		Repetir = repetir;
	}



	public String getRazón() {
		return Razón;
	}



	public void setRazón(String razón) {
		Razón = razón;
	}



	public String getDescripcióndelvalor() {
		return Descripcióndelvalor;
	}



	public void setDescripcióndelvalor(String descripcióndelvalor) {
		Descripcióndelvalor = descripcióndelvalor;
	}



	public String getISIN() {
		return ISIN;
	}



	public void setISIN(String iSIN) {
		ISIN = iSIN;
	}



	public String getEnviado() {
		return Enviado;
	}



	public void setEnviado(String enviado) {
		Enviado = enviado;
	}



	public String getReceived() {
		return Received;
	}



	public void setReceived(String received) {
		Received = received;
	}



	public String getTipodeoperación() {
		return Tipodeoperación;
	}



	public void setTipodeoperación(String tipodeoperación) {
		Tipodeoperación = tipodeoperación;
	}



	public String getTradeSubtype() {
		return TradeSubtype;
	}



	public void setTradeSubtype(String tradeSubtype) {
		TradeSubtype = tradeSubtype;
	}



	public String getTradingMode() {
		return TradingMode;
	}



	public void setTradingMode(String tradingMode) {
		TradingMode = tradingMode;
	}



	public String getTradingPlatform() {
		return TradingPlatform;
	}



	public void setTradingPlatform(String tradingPlatform) {
		TradingPlatform = tradingPlatform;
	}



	public String getTradingSystem() {
		return TradingSystem;
	}



	public void setTradingSystem(String tradingSystem) {
		TradingSystem = tradingSystem;
	}



	public String getCapacity() {
		return Capacity;
	}



	public void setCapacity(String capacity) {
		Capacity = capacity;
	}



	public String getCantidad() {
		return Cantidad;
	}



	public void setCantidad(String cantidad) {
		Cantidad = cantidad;
	}



	public String getTipodecantidad() {
		return Tipodecantidad;
	}



	public void setTipodecantidad(String tipodecantidad) {
		Tipodecantidad = tipodecantidad;
	}



	public String getPrecio() {
		return Precio;
	}



	public void setPrecio(String precio) {
		Precio = precio;
	}



	public String getDivisadelprecio() {
		return Divisadelprecio;
	}



	public void setDivisadelprecio(String divisadelprecio) {
		Divisadelprecio = divisadelprecio;
	}



	public String getPriceType() {
		return PriceType;
	}



	public void setPriceType(String priceType) {
		PriceType = priceType;
	}



	public String getCentro() {
		return Centro;
	}



	public void setCentro(String centro) {
		Centro = centro;
	}



	public String getFechadetransacción() {
		return Fechadetransacción;
	}



	public void setFechadetransacción(String fechadetransacción) {
		Fechadetransacción = fechadetransacción;
	}



	public String getAssetClass() {
		return AssetClass;
	}



	public void setAssetClass(String assetClass) {
		AssetClass = assetClass;
	}



	public String getOrderType() {
		return OrderType;
	}



	public void setOrderType(String orderType) {
		OrderType = orderType;
	}



	public String getOrderAccountType() {
		return OrderAccountType;
	}



	public void setOrderAccountType(String orderAccountType) {
		OrderAccountType = orderAccountType;
	}



	public String getCosts() {
		return Costs;
	}



	public void setCosts(String costs) {
		Costs = costs;
	}



	public String getRebates() {
		return Rebates;
	}



	public void setRebates(String rebates) {
		Rebates = rebates;
	}



	public String getLastCapacity() {
		return LastCapacity;
	}



	public void setLastCapacity(String lastCapacity) {
		LastCapacity = lastCapacity;
	}



	public String getBestBid() {
		return BestBid;
	}



	public void setBestBid(String bestBid) {
		BestBid = bestBid;
	}



	public String getBestOffer() {
		return BestOffer;
	}



	public void setBestOffer(String bestOffer) {
		BestOffer = bestOffer;
	}



	public String getIDdeejecutor() {
		return IDdeejecutor;
	}



	public void setIDdeejecutor(String iDdeejecutor) {
		IDdeejecutor = iDdeejecutor;
	}



	public String getTipodeejecutor() {
		return Tipodeejecutor;
	}



	public void setTipodeejecutor(String tipodeejecutor) {
		Tipodeejecutor = tipodeejecutor;
	}



	public String getIDdecomprador() {
		return IDdecomprador;
	}



	public void setIDdecomprador(String iDdecomprador) {
		IDdecomprador = iDdecomprador;
	}



	public String getTipodecomprador() {
		return Tipodecomprador;
	}



	public void setTipodecomprador(String tipodecomprador) {
		Tipodecomprador = tipodecomprador;
	}



	public String getIDdevendedor() {
		return IDdevendedor;
	}



	public void setIDdevendedor(String iDdevendedor) {
		IDdevendedor = iDdevendedor;
	}



	public String getTipodevendedor() {
		return Tipodevendedor;
	}



	public void setTipodevendedor(String tipodevendedor) {
		Tipodevendedor = tipodevendedor;
	}



	public String getÚltmensaje() {
		return Últmensaje;
	}



	public void setÚltmensaje(String últmensaje) {
		Últmensaje = últmensaje;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Trade [");
		if (IDdetransacción != null && !IDdetransacción.equals("")) {
			builder.append(IDdetransacción);
			builder.append(", ");
		}
		if (Version != null && !Version.equals("")) {
			builder.append(Version);
			builder.append(", ");
		}
		if (Acción != null && !Acción.equals("")) {
			builder.append(Acción);
			builder.append(", ");
		}
		if (Estatus != null && !Estatus.equals("")) {
			builder.append(Estatus);
			builder.append(", ");
		}
		if (FeedProducer != null && !FeedProducer.equals("")) {
			builder.append(FeedProducer);
			builder.append(", ");
		}
		if (Repetir != null && !Repetir.equals("")) {
			builder.append(Repetir);
			builder.append(", ");
		}
		if (Razón != null && !Razón.equals("")) {
			builder.append(Razón);
			builder.append(", ");
		}
		if (Descripcióndelvalor != null && !Descripcióndelvalor.equals("")) {
			builder.append(Descripcióndelvalor);
			builder.append(", ");
		}
		if (ISIN != null && !ISIN.equals("")) {
			builder.append(ISIN);
			builder.append(", ");
		}
		if (Enviado != null && !Enviado.equals("")) {
			builder.append(Enviado);
			builder.append(", ");
		}
		if (Received != null && !Received.equals("")) {
			builder.append(Received);
			builder.append(", ");
		}
		if (Tipodeoperación != null && !TradeSubtype.equals("")) {
			builder.append(Tipodeoperación);
			builder.append(", ");
		}
		if (TradeSubtype != null && !TradeSubtype.equals("")) {
			builder.append(TradeSubtype);
			builder.append(", ");
		}
		if (TradingMode != null && !TradingMode.equals("")) {
			builder.append(TradingMode);
			builder.append(", ");
		}
		if (TradingPlatform != null && !TradingPlatform.equals("")) {
			builder.append(TradingPlatform);
			builder.append(", ");
		}
		if (TradingSystem != null && !TradingSystem.equals("")) {
			builder.append(TradingSystem);
			builder.append(", ");
		}
		if (Capacity != null && !Capacity.equals("")) {
			builder.append(Capacity);
			builder.append(", ");
		}
		if (Cantidad != null && !Cantidad.equals("")) {
			builder.append(Cantidad);
			builder.append(", ");
		}
		if (Tipodecantidad != null && !Tipodecantidad.equals("")) {
			builder.append(Tipodecantidad);
			builder.append(", ");
		}
		if (Precio != null && !Precio.equals("")) {
			builder.append(Precio);
			builder.append(", ");
		}
		if (Divisadelprecio != null && !Divisadelprecio.equals("")) {
			builder.append(Divisadelprecio);
			builder.append(", ");
		}
		if (PriceType != null && !PriceType.equals("")) {
			builder.append(PriceType);
			builder.append(", ");
		}
		if (Centro != null && !Centro.equals("")) {
			builder.append(Centro);
			builder.append(", ");
		}
		if (Fechadetransacción != null && !Fechadetransacción.equals("")) {
			builder.append(Fechadetransacción);
			builder.append(", ");
		}
		if (AssetClass != null && !AssetClass.equals("")) {
			builder.append(AssetClass);
			builder.append(", ");
		}
		if (OrderType != null && !OrderType.equals("")) {
			builder.append(OrderType);
			builder.append(", ");
		}
		if (OrderAccountType != null && !OrderAccountType.equals("")) {
			builder.append(OrderAccountType);
			builder.append(", ");
		}
		if (Costs != null && !Costs.equals("")) {
			builder.append(Costs);
			builder.append(", ");
		}
		if (Rebates != null && !Rebates.equals("")) {
			builder.append(Rebates);
			builder.append(", ");
		}
		if (LastCapacity != null && !LastCapacity.equals("")) {
			builder.append(LastCapacity);
			builder.append(", ");
		}
		if (BestBid != null && !BestBid.equals("")) {
			builder.append(BestBid);
			builder.append(", ");
		}
		if (BestOffer != null && !BestOffer.equals("")) {
			builder.append(BestOffer);
			builder.append(", ");
		}
		if (IDdeejecutor != null && !IDdeejecutor.equals("")) {
			builder.append(IDdeejecutor);
			builder.append(", ");
		}
		if (Tipodeejecutor != null && !Tipodeejecutor.equals("")) {
			builder.append(Tipodeejecutor);
			builder.append(", ");
		}
		if (IDdecomprador != null && !IDdecomprador.equals("")) {
			builder.append(IDdecomprador);
			builder.append(", ");
		}
		if (Tipodecomprador != null && !Tipodecomprador.equals("")) {
			builder.append(Tipodecomprador);
			builder.append(", ");
		}
		if (IDdevendedor != null && !IDdevendedor.equals("")) {
			builder.append(IDdevendedor);
			builder.append(", ");
		}
		if (Tipodevendedor != null && !Tipodevendedor.equals("")) {
			builder.append(Tipodevendedor);
			builder.append(", ");
		}
		if (Últmensaje != null && !Últmensaje.equals("")) {
			builder.append(Últmensaje);
		}
		builder.append("]");
		return builder.toString();
	}
	
	
	
	@Override
	public int hashCode() {
		 int result = 17;
	        result = 31 * result + getEstatus().hashCode();
	        result = 31 * result + getAssetClass().hashCode();
	        
	        result = 31 * result + getCentro().hashCode();
	        result = 31 * result + getAssetClass().hashCode();
	        result = 31 * result + getDivisadelprecio().hashCode();
	        result = 31 * result + getDivisadelprecio().hashCode();
	        result = 31 * result + getIDdecomprador().hashCode();
	        result = 31 * result + getTipodevendedor().hashCode();
	        result = 31 * result + getIDdevendedor().hashCode();

	        
		return result;
	}




	public long regexFind() {
		long total=17;
		// REGEX that matches 1 or more white space
			Pattern patternOp = Pattern.compile("\\d");
			Matcher matcherOp = patternOp.matcher(getVersion());
			while(matcherOp.find()) {
				total = 31 * total +Long.parseLong(matcherOp.group());
			}
			
			Matcher fechaOP = patternOp.matcher(getFechadetransacción());
			while(fechaOP.find()) {
				total = 31 * total +Long.parseLong(fechaOP.group());
			}
			
			Matcher fechasend = patternOp.matcher(getEnviado());
			while(fechasend.find()) {
				total = 31 * total +Long.parseLong(fechasend.group());
			}
			
			Matcher cantidad = patternOp.matcher(getCantidad());
			while(cantidad.find()) {
				total = 31 * total +Long.parseLong(cantidad.group());
			}
			
			Matcher precio = patternOp.matcher(getPrecio());
			while(precio.find()) {
				total = 31 * total + Long.parseLong(precio.group());
			}
			Matcher trans = patternOp.matcher(getIDdetransacción());
			while(trans.find()) {
				total = 31 * total +Long.parseLong(trans.group());
			}
			Matcher isn = patternOp.matcher(getISIN());
			while(isn.find()) {
				total = 31 * total +Long.parseLong(isn.group());
			}
			Matcher cost = patternOp.matcher(getCosts());
			while(cost.find()) {
				total = 31 * total +Long.parseLong(cost.group());
			}
			total=31 * total+hashCode();
			return total;
	}
	
}
