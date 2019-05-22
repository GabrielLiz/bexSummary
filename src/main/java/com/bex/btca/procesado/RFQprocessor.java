package com.bex.btca.procesado;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.item.ItemProcessor;

import com.bex.btca.model.EstadisticasRFQ;
import com.bex.btca.model.Totales;

public class RFQprocessor implements ItemProcessor<Totales, EstadisticasRFQ> {
	// EQC
	public String EQC_BTCA_ORDER = "^BBVAEQC_BTCA_ORDER_\\d{8}";
	public String EQC_BTCA_PLACEMENT = "^BBVAEQC_BTCA_PLACEMENT_\\d{8}";
	// FX
	public String T360T = "^BBVA360T_BTCA_RFQ_\\d{8}";
	public String FXALL = "^BBVAFXALL_BTCA_RFQ_\\d{8}";
	public String RET = "^BBVARET_BTCA_RFQ_\\d{8}";
	public String BBG = "^BBVABBG_BTCA_RFQ_\\d{8}";
	public String UM_BTCA_RFQ = "^BBVAUM_BTCA_RFQ_\\d{8}";
	public String FNC_BTCA_RFQ = "^BBVAFNC_BTCA_RFQ_\\d{8}";
	// EQD
	public String _FLOW = "^BBVAEQD_BTCA_RFQ_\\d{8}_FLOW";
	public String EQD = "^BBVAEQD_BTCA_RFQ_\\d{8}";

	// EQDL
	public String EQDL_BTCA_ORDER = "^BBVAEQDL_BTCA_ORDER_\\d{8}";
	public String EQDL_BTCA_PLACEMENT = "^BBVAEQDL_BTCA_PLACEMENT_\\d{8}";
	// SBP
	public String SBP_BTCA_RFQ = "^BBVASBP_BTCA_RFQ_\\d{8}";

	ArrayList<String> listaRFQ;

	public RFQprocessor() {
		super();
		// una lista simple con todas las mascaras quenecesitos
		listaRFQ = new ArrayList<String>();
		listaRFQ.add(EQC_BTCA_ORDER);
		listaRFQ.add(EQC_BTCA_PLACEMENT);
		listaRFQ.add(T360T);
		listaRFQ.add(FXALL);
		listaRFQ.add(RET);
		listaRFQ.add(BBG);
		listaRFQ.add(UM_BTCA_RFQ);
		listaRFQ.add(FNC_BTCA_RFQ);
		listaRFQ.add(_FLOW);
		listaRFQ.add(EQD);
		listaRFQ.add(EQDL_BTCA_ORDER);
		listaRFQ.add(EQDL_BTCA_PLACEMENT);
		listaRFQ.add(SBP_BTCA_RFQ);

	}

	// Por cada item que es un registro leido por el Reader
	//
	@Override
	public EstadisticasRFQ process(Totales item) throws Exception {
		String va = null;
		// devuelve null si no encontra el regex 
		for (String string : listaRFQ) {
			va = regexFind(string, item.getVersion(), item.getStatus());
			if (va != null) {
				return new EstadisticasRFQ(va, "F " + item.getFecha_operativa());
			}
		}
		return null;
	}
// Busca los regex de la lista
	public String regexFind(String regex, String text, String tex2) {
		// REGEX that matches 1 or more white space
		Pattern patternOp = Pattern.compile(regex);
		Matcher matcherOp = patternOp.matcher(text);
		if (matcherOp.find()) {
			return matcherOp.group() + ";" + tex2;
		}
		return null;
	}

}
