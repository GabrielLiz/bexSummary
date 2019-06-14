package com.bex.btca.utils;

import com.bex.btca.procesado.RFQprocessor;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import ch.qos.logback.core.CoreConstants;

import static com.bex.btca.utils.FilesSheetsDrive.*;

public class UploadResults {
    private ArrayList<String> Eqc = new ArrayList<>();
    private ArrayList<String> EqdFlow = new ArrayList<>();
    private ArrayList<String> Eqdl = new ArrayList<>();
    private ArrayList<String> Fxall = new ArrayList<>();
    private ArrayList<String> UM = new ArrayList<>();
    private ArrayList<String> TV360T = new ArrayList<>();
    private ArrayList<String> TVRET = new ArrayList<>();
    private ArrayList<String> TVBBG = new ArrayList<>();
    private ArrayList<String> TVSBP = new ArrayList<>();
    private ArrayList<String> FNC = new ArrayList<>();
    private HashMap<String, ArrayList<String>> FicherosOperativa = new HashMap<>();

    private int month;
    private int year;

    public UploadResults() {


        FicherosOperativa.put(EQC_BTCA_ORDER, Eqc);
        FicherosOperativa.put(EQC_BTCA_PLACEMENT, Eqc);
        FicherosOperativa.put(_FLOW, EqdFlow);
        FicherosOperativa.put(EQDL_BTCA_ORDER, Eqdl);
        FicherosOperativa.put(EQDL_BTCA_PLACEMENT, Eqdl);
        FicherosOperativa.put(FXALL, Fxall);
        FicherosOperativa.put(UM_BTCA_RFQ, UM);
        FicherosOperativa.put(T360T, TV360T);
        FicherosOperativa.put(RET, TVRET);
        FicherosOperativa.put(BBG, TVBBG);
        FicherosOperativa.put(SBP_BTCA_RFQ, TVSBP);
        FicherosOperativa.put(FNC_BTCA_RFQ, FNC);
        loadConfig();

    }


    private String obtenerHojaQ(String fecha, String version) {
        int valueQ=0;
        dateMaker(fecha);
        int mes = month;

        if (year == 2019) {
            if (mes <= 3) {
                valueQ = 4;
            } else if (mes <= 6) {
                valueQ = 5;
            } else if (mes <= 9) {
                valueQ = 6;
            } else {
                valueQ = 7;

            }
        } else if(year==2018){
            if (mes <= 3) {
                valueQ = 0;
            } else if (mes <= 6) {
                valueQ = 1;
            } else if (mes <= 9) {
                valueQ = 2;
            } else {
                valueQ = 3;

            }
        }else if(year==2020){
            if (mes <= 3) {
                valueQ = 8;
            } else if (mes <= 6) {
                valueQ = 9;
            } else if (mes <= 9) {
                valueQ = 10;
            } else {
                valueQ = 11;

            }
        }

        ArrayList<String> values = FicherosOperativa.get(version);
        System.out.println(version + " " + values.get(valueQ));
        return values.get(valueQ);
    }

    public void actualizarDatos(String version, String fecha, String Status, String value) throws IOException, GeneralSecurityException {
        ConectionSheets con = new ConectionSheets();
        con.actualizar(obtenerHojaQ(fecha, version), fecha, Status, value, version);
    }

    private void dateMaker(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        month = localDate.getMonthValue();
        year = localDate.getYear();
    }

    public void loadConfig() {
        String valores;
        String plataforma = null;
        try {
            StringBuilder builder = new StringBuilder();
            FileReader fileRead = new FileReader("c:/CSV/config.txt"); //Set true for append mode
            int valor = fileRead.read();

            while (valor != -1) {
                builder.append((char) valor);
                valor = fileRead.read();
            }
            StringTokenizer linea = new StringTokenizer(builder.toString(), "#\r\n");
            while (linea.hasMoreTokens()) {
                valores=linea.nextToken();

                for (String lista : FilesSheetsDrive.listas()) {
                    if (valores.equals(lista)) {
                        plataforma = valores;
                    }
                }

                if (!valores.equals(plataforma)) {
                  if(  valores.startsWith(":")){
                      System.out.println(valores);
                  }else {
                     FicherosOperativa.get(plataforma).add(valores);
                  }
                }

            }
            fileRead.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
