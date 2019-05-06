package com.bex.btca.procesado;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
	private int borrado=0;
	public BTCAprocessor() {
		super();
	}

	@Override
	public Totales process(Trade item) throws Exception {
		Long value=new BigInteger(makeSHA1Hash(item.toString()), 16).longValue();
		if (seenUsers.contains(value)) {
			System.out.println(item.toString()+" "+(borrado+=1));
			return null;
			
		} else {
			seenUsers.add(value);	
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
	
	

	  public  String makeSHA1Hash(String input)
	            throws NoSuchAlgorithmException, UnsupportedEncodingException
	        {
	            MessageDigest md = MessageDigest.getInstance("SHA1");
	            md.reset();
	            byte[] buffer = input.getBytes("UTF-8");
	            md.update(buffer);
	            byte[] digest = md.digest();

	            String hexStr = "";
	            for (int i = 0; i < digest.length; i++) {
	                hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
	            }
	            return hexStr;
	        }
	
	
	

}
