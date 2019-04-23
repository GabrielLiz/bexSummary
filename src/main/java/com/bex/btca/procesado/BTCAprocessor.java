package com.bex.btca.procesado;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.batch.item.ItemProcessor;

import com.bex.btca.model.Totales;
import com.bex.btca.model.Trade;

public class BTCAprocessor implements ItemProcessor<Trade, Totales> {

	String regexOperativa = "[0-9]{1,2}(/|-)[0-9]{1,2}(/|-)[0-9]{4}";
	String regexSubida = "[0-9]{1,2}(:)[0-9]{1,2}";
	private Set<Long> seenUsers = new HashSet<Long>();

	public BTCAprocessor() {
		super();
	}

	@Override
	public Totales process(Trade item) throws Exception {
		if (seenUsers.contains(item.regexFind())) {
			System.out.println(item.toString());
			return null;
			
		} else {
			seenUsers.add(item.regexFind());	
			item.setProcesado(true);
			String operativa = regexReplace(regexOperativa, item.getFechadetransacción());
			String subidaMa = regexReplace(regexSubida, item.getEnviado());
			Totales totales = new Totales(item.getIDdetransacción().toString(), item.getVersion(), item.getEstatus(),
					item.getISIN(), subidaMa, item.getTipodeoperación(), operativa, item.getAssetClass());

			return totales;
		}
		

	}

	public String regexReplace(String regex, String texts) {
		// REGEX that matches 1 or more white space
		if (!texts.equals("")) {
			Pattern patternOp = Pattern.compile(regex);
			Matcher matcherOp = patternOp.matcher(texts.toString());
			if (matcherOp.find()) {
				return matcherOp.group();
			}
		}

		return "";
	}
	
	
	public Long toHex(String arg) {
		 return Long.decode( String.format("%040x", new BigInteger(1, arg.getBytes(/*"UTF-8*/))));
	}
	
	

}
