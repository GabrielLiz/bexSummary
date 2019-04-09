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
	private String regexUM = "([a-zA-Z0-9]{2}\\-\\d{1}\\-[A-Z0-9]{9}\\.\\d{1}\\.[F]\\:\\d\\:\\d)|([a-zA-Z0-9]{2}\\-\\d{1}\\-[A-Z0-9]{9})";
	private String regexEQC = "^([a-zA-Z0-9]{16})|^([a-zA-Z:\\-0-9]+.(2020|2019|2018))|^([0-9]+\\.[1234]\\.[0-9]+)$";
	private String BBG_360T = "^[8][0-9]+_[01]";
	private String SBP = "^(([a-zA-Z0-9]*)(?:[-]))+[a-zA-Z0-9]*_[012]";
	private String RET = "^[0-9]+_[01].[0-9]+";

	public TradesWriter(DataSource data) {
		super();
		this.jdbctemplate = new JdbcTemplate(data);

	}

	@Override
	public void write(List<? extends Totales> item) throws Exception {

		for (Totales totales : item) {
			String valueIndex = totales.getStatus() + ";" + "F " + totales.getFecha_operativa() + ";"
					+ totales.getAssset_class() + ";" + regexFind(totales.getId_trans(), totales.getSent()) + ";"
					+ totales.getSent();
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

	public String regexFind(String texts, String hor) {
		// REGEX that matches 1 or more white space
		String[] parts = hor.split(Pattern.quote(":"));
		int minuto = Integer.parseInt(parts[1]);
		// Busqueda EQC
		Pattern patternEQC = Pattern.compile(regexEQC);
		Matcher matcherEQC = patternEQC.matcher(texts.toString());
		if (matcherEQC.find()) {
			if (parts[0].equals("17")) {
				if (minuto >= 30 && minuto < 60) {
					return "EQC";
				}
			} else {
				return "EQC *";
			}

		}

		/// BUSQUEDA de UM
		Pattern patternUM = Pattern.compile(regexUM);
		Matcher matcherUM = patternUM.matcher(texts.toString());
		if (matcherUM.find()) {
			if (parts[0].equals("01")) {
				return "UM";
			}else {
				return "UM*";
			}
		}

		/// BUSQUEDA de SBP
		Pattern patternSBP = Pattern.compile(SBP);
		Matcher matcherSBP = patternSBP.matcher(texts.toString());
		if (matcherSBP.find()) {
				return "SBP";
		}

		/// BUSQUEDA 360 y BBG FXAL
		Pattern patternBB30 = Pattern.compile(BBG_360T);
		Matcher matcherBB30 = patternBB30.matcher(texts.toString());
		if (matcherBB30.find()) {

			if (parts[0].equals("22")) {
				if (minuto >= 30 && minuto < 60) {
					return "BBG";
				}
			}
			if (parts[0].equals("23")) {
				if (minuto >= 30 && minuto < 60) {
					return "360T";
				}
			}
			if (parts[0].equals("00")) {
				if (minuto >= 30 && minuto < 60) {
					return "FXAll";
				}
			}
		}

		// RET
		Pattern patternRET = Pattern.compile(RET);
		Matcher matcherRET = patternRET.matcher(texts.toString());
		if (matcherRET.find()) {
			if (parts[0].equals("21")) {
				if (minuto >= 30 && minuto < 60) {
					return "RET";
				}
			} else {
				return "RET*";
			}
		}

		return "";

	}

}
