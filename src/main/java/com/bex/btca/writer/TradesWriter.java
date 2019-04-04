package com.bex.btca.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bex.btca.model.EstadisticasTrade;
import com.bex.btca.model.Totales;

public class TradesWriter implements ItemWriter<Totales> {
	FlatFileItemWriter<Totales> flatitemwriter;
	// Lista simple indexada por nombre BTCA
	private HashMap<String, Integer> horasDelDia = new HashMap<String, Integer>();
	
	private JdbcTemplate jdbctemplate;

	public TradesWriter(DataSource data) {
		super();
		this.jdbctemplate = new JdbcTemplate(data);

	}



	@Override
	public void write(List<? extends Totales> item) throws Exception {
		
		for(Totales totales:item)
			{
			String valueIndex = totales.getStatus() + ";" +"F "+totales.getFecha_operativa() + ";" + totales.getAssset_class()+ ";"+totales.getSent();
			if (horasDelDia.get(valueIndex) == null) {
				horasDelDia.put(valueIndex, 1 );
				
				jdbctemplate.update("INSERT INTO trade (busqueda, valores) VALUES (?,?)",valueIndex.toString(),1);

			} else {
				int v = horasDelDia.get(valueIndex);
				// recupero el valor de la segunda lista recuperada que el valor que devuelve es
				// un entero.
				horasDelDia.put(valueIndex, v=v+1);
			}
		}
		
		 for (Map.Entry<String, Integer> entry : horasDelDia.entrySet()) {
				//	jdbctemplate.update("UPDATE trade SET busqueda = '"+entry.getKey()+"', valores ='"+entry.getValue()+"' WHERE busqueda = '"+entry.getKey()+"'");
					jdbctemplate.update("UPDATE  trade SET valores = ? WHERE busqueda = ?",entry.getValue(),entry.getKey().toString());
		 
		 }

		
	}

	
	
	
	public boolean regexFind(String regex, String texts) {
		// REGEX that matches 1 or more white space
		Pattern patternOp = Pattern.compile(regex);
		Matcher matcherOp = patternOp.matcher(texts.toString());

		return matcherOp.find();
	}
	

	public void write(StringBuilder dato) {
		File file = new File("/CSV/Trades.csv");

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
