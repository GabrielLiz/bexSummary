package com.bex.btca.utils;

import java.util.ArrayList;

public class FilesSheetsDrive {

    // EQC
    public static final String EQC_BTCA_ORDER = "EQC_BTCA_ORDER";
    public static final String EQC_BTCA_PLACEMENT = "EQC_BTCA_PLACEMENT";
    // FX
    public static final String T360T = "360T_BTCA_RFQ";
    public static final String FXALL = "FXALL_BTCA_RFQ";
    public static final String RET = "RET_BTCA_RFQ";
    public static final String BBG = "BBG_BTCA_RFQ";
    public static final String UM_BTCA_RFQ = "UM_BTCA_RFQ";
    public static final String FNC_BTCA_RFQ = "FNC_BTCA_RFQ";
    // EQD
    public static final String _FLOW = "FLOW";
    public static final String EQD = "EQD_BTCA_RFQ";

    // EQDL
    public static final String EQDL_BTCA_ORDER = "EQDL_BTCA_ORDER";
    public static final String EQDL_BTCA_PLACEMENT = "EQDL_BTCA_PLACEMENT";
    // SBP
    public static final String SBP_BTCA_RFQ = "SBP_BTCA_RFQ";


    public static ArrayList<String> listas(){
         ArrayList<String> lista= new ArrayList<>();
        lista.add(EQC_BTCA_ORDER);
        lista.add(EQC_BTCA_PLACEMENT);
        lista.add(T360T);
        lista.add(FXALL);
        lista.add(RET);
        lista.add(BBG);
        lista.add(UM_BTCA_RFQ);
        lista.add(FNC_BTCA_RFQ);
        lista.add(_FLOW);
        lista.add(EQDL_BTCA_ORDER);
        lista.add(EQDL_BTCA_PLACEMENT);
        lista.add(SBP_BTCA_RFQ);
        return lista;
    }
}
