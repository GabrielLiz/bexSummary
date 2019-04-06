package com.bex.btca.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bex.btca.model.Totales;

public class TradesWriter implements ItemWriter<Totales> {
	FlatFileItemWriter<Totales> flatitemwriter;
	// Lista simple indexada por nombre BTCA
	private HashMap<String, Integer> horasDelDia = new HashMap<String, Integer>();

	private JdbcTemplate jdbctemplate;
	private String regexUM = "^(([a-zA-Z0-9]{2})-(\\d{1})-([A-Z0-9]{9}))";
	private String regexFXALL = "^(([0-9]{7})_([0-3]))";
	private String regexEQC =("^([a-zA-Z0-9]{16})|^(([0-9]).([0-9]{20}))");

	public TradesWriter(DataSource data) {
		super();
		this.jdbctemplate = new JdbcTemplate(data);

	}

	@Override
	public void write(List<? extends Totales> item) throws Exception {

		for (Totales totales : item) {
			String valueIndex = totales.getStatus() + ";" + "F " + totales.getFecha_operativa() + ";"
					+ totales.getAssset_class() + ";" + regexFind(totales.getId_trans(),totales.getSent()	) + ";" + totales.getSent();
			if (horasDelDia.get(valueIndex) == null) {
				horasDelDia.put(valueIndex, 1);

				jdbctemplate.update("INSERT INTO trade (busqueda, valores) VALUES (?,?)", valueIndex.toString(), 1);

			} else {
				int v = horasDelDia.get(valueIndex);
				// recupero el valor de la segunda lista recuperada que el valor que devuelve es
				// un entero.
				horasDelDia.put(valueIndex, v = v + 1);
			}
		}

		for (Map.Entry<String, Integer> entry : horasDelDia.entrySet()) {
			// jdbctemplate.update("UPDATE trade SET busqueda = '"+entry.getKey()+"',
			// valores ='"+entry.getValue()+"' WHERE busqueda = '"+entry.getKey()+"'");
			jdbctemplate.update("UPDATE  trade SET valores = ? WHERE busqueda = ?", entry.getValue(),
					entry.getKey().toString());

		}

	}

	public String regexFind(String texts,String hor) {
		// REGEX that matches 1 or more white space
		String[] parts = hor.split(Pattern.quote(":"));
		

		Pattern patternUM = Pattern.compile(regexUM);
		Matcher matcherUM = patternUM.matcher(texts.toString());
		if (matcherUM.find()) {
			
			return "UM";
		}
		Pattern patternFX = Pattern.compile(regexFXALL);
		Matcher matcherFX = patternFX.matcher(texts.toString());
		if (matcherFX.find()) {
			return "FXALL";
		}
		Pattern patternEQC = Pattern.compile(regexEQC);
		Matcher matcherEQC = patternEQC.matcher(texts.toString());
		if (matcherEQC.find()) {
			if(parts[0].equals("17")) {
				int minuto=Integer.parseInt(parts[1]);
				if(minuto>=30&&minuto<60) {
					return "EQC 90% seguro";
				}
			
			}
			
		}
		return "";

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
