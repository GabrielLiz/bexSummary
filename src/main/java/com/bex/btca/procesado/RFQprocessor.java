package com.bex.btca.procesado;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.item.ItemProcessor;

import com.bex.btca.model.EstadisticasRFQ;
import com.bex.btca.model.Totales;
import com.bex.btca.utils.FilesSheetsDrive;

import static com.bex.btca.utils.FilesSheetsDrive.BBG;
import static com.bex.btca.utils.FilesSheetsDrive.EQC_BTCA_ORDER;
import static com.bex.btca.utils.FilesSheetsDrive.EQC_BTCA_PLACEMENT;
import static com.bex.btca.utils.FilesSheetsDrive.EQD;
import static com.bex.btca.utils.FilesSheetsDrive.EQDL_BTCA_ORDER;
import static com.bex.btca.utils.FilesSheetsDrive.EQDL_BTCA_PLACEMENT;
import static com.bex.btca.utils.FilesSheetsDrive.FNC_BTCA_RFQ;
import static com.bex.btca.utils.FilesSheetsDrive.FXALL;
import static com.bex.btca.utils.FilesSheetsDrive.SBP_BTCA_RFQ;
import static com.bex.btca.utils.FilesSheetsDrive.T360T;
import static com.bex.btca.utils.FilesSheetsDrive.UM_BTCA_RFQ;
import static com.bex.btca.utils.FilesSheetsDrive._FLOW;
import static com.bex.btca.utils.FilesSheetsDrive.RET;

public class RFQprocessor implements ItemProcessor<Totales, EstadisticasRFQ> {

	public static final String fecha_estractor ="\\d{8}";


	private ArrayList<String> listaRFQ;

	public RFQprocessor() {
		super();
		// una lista simple con todas las mascaras quenecesitos
		listaRFQ = FilesSheetsDrive.listas();

	}

	// Por cada item que es un registro leido por el Reader
	//
	@Override
	public EstadisticasRFQ process(Totales item) throws Exception {
		String va = null;
		// devuelve null si no encontra el regex 
		for (String string : listaRFQ) {
			String tx =regexFind(string, item.getVersion());
			if (tx != null) {
				va = tx+";"+item.getStatus()+";"+dateMaker(fecha_estractor,item.getVersion());

				return new EstadisticasRFQ(va, item.getFecha_operativa());
			}
		}
		return null;
	}
// Busca los regex de la lista
	private String regexFind(String regex, String text) {
		// REGEX that matches 1 or more white space
		Pattern patternOp = Pattern.compile(regex);
		Matcher matcherOp = patternOp.matcher(text);
		if (matcherOp.find()) {
			return matcherOp.group();
		}
		return null;
	}

	private void dateMaker(String date) {

	}

	private String dateMaker(String regex, String value) {
		String date =regexFind(regex, value);
		final String OLD_FORMAT = "yyyyMMdd";
		final String NEW_FORMAT = "d/MM/yyyy";
		// August 12, 2010
		String newDateString= null;

		SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
		Date d = null;
		try {
		d = sdf.parse(date);
		sdf.applyPattern(NEW_FORMAT);
		newDateString = sdf.format(d);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newDateString;
	}
	
}
