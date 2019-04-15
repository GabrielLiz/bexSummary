package com.bex.btca.procesado;

import java.util.HashMap;

import javax.activation.DataSource;

import org.springframework.batch.item.ItemProcessor;

import com.bex.btca.model.EstadisticasRFQ;
import com.bex.btca.model.Totales;

public class TradeProcesor implements ItemProcessor<Totales, EstadisticasRFQ> {
	private HashMap<String, Integer> horasDelDia = new HashMap<String, Integer>();
	
	
	
	public TradeProcesor(DataSource data) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public EstadisticasRFQ process(Totales item) throws Exception {
			String valueIndex = item.getStatus() + ";" + item.getFecha_operativa() + ";"
					+ item.getAssset_class() + ";" + item.getSent();
			if (horasDelDia.get(valueIndex) == null) {
				horasDelDia.put(valueIndex, 1);
			} else {
				int v = horasDelDia.get(valueIndex);
				// recupero el valor de la segunda lista recuperada que el valor que devuelve es
				// un entero.
				horasDelDia.put(valueIndex, v = v + 1);
			}

		EstadisticasRFQ esta= new EstadisticasRFQ(null,null);
		
		return null;
	}

}
