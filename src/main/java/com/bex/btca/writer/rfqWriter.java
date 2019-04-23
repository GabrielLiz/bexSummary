package com.bex.btca.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.batch.item.ItemWriter;

import com.bex.btca.model.Totales;

public class rfqWriter implements ItemWriter<Totales> {
	ArrayList<String> listaRFQ = new ArrayList<String>();
	public int valir;

	// Lista simple indexada por nombre BTCA
	private HashMap<String, Integer> mapVerticales = new HashMap<String, Integer>();
	// Lista Bidmencional
	private HashMap<String, Integer> mapFechas = new HashMap<String, Integer>();

	public rfqWriter() {
		super();

		// una lista simple con todas las mascaras quenecesitos

		// Inicio los valores de la primera lista con el array y un valor por defecto de
		// 0
		for (String string : listaRFQ) {
			mapVerticales.put(string, 0);
		}
	}

	@Override
	public void write(List<? extends Totales> item) throws Exception {

		for (Totales totales : item) {
			for (String lista : listaRFQ) {
				String valor = ".+" + lista;
				if (regexFind(valor, totales.getVersion())) {
					String valueIndex = totales.getStatus() + ";" +"'" +totales.getFecha_operativa()+ ";"+ lista;
					if (mapFechas.get(valueIndex) == null) {
						 mapFechas.put(valueIndex, 1);
					} else {
						// obtengo el objeto primario que contine la lista de plataformas  separado por fecha y status
						
						//recupero el valor de la primera lista que es otra lista
						int v =  mapFechas.get(valueIndex);
						// recupero el valor de la segunda lista recuperada que el valor que devuelve es un entero.
						mapFechas.put(valueIndex, v = v + 1);
				
					}

				}
			}

		}
		
		
		/*
		Iterator it = mapFechas.entrySet().iterator();
    	StringBuilder bu= new StringBuilder();
    	bu.append("Status; Fecha de operativa; Plataforma; Total"+System.getProperty("line.separator"));
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        String va=pair.getKey() + ";" + pair.getValue().toString()+System.getProperty("line.separator");
		        write(bu.append(va));
		    }
		    
		    */

	}

	public boolean regexFind(String regex, String texts) {
		// REGEX that matches 1 or more white space
		Pattern patternOp = Pattern.compile(regex);
		Matcher matcherOp = patternOp.matcher(texts.toString());

		return matcherOp.find();
	}
	
	public void write(StringBuilder dato) {
		File file = new File("/CSV/Rfqs.csv");

		FileWriter fr = null;
		BufferedWriter br = null;
		try {
			fr = new FileWriter(file);
			br = new BufferedWriter(fr);

			br.write(dato.toString());
			/*
			 * for (String fecha : fechaOpe) { br.write(fecha+
			 * System.getProperty("line.separator")); for (String string : horadesbida) {
			 * br.write(string+ System.getProperty("line.separator")); }
			 * System.getProperty("line.separator"); }
			 */

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
