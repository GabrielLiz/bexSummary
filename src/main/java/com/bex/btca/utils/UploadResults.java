package com.bex.btca.utils;

import com.bex.btca.procesado.RFQprocessor;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import ch.qos.logback.core.CoreConstants;

import static com.bex.btca.utils.FilesSheetsDrive.*;

public class UploadResults {
    private ArrayList<String> Eqc=new ArrayList<>();
    private ArrayList<String> EqdFlow=new ArrayList<>();
    private ArrayList<String> Eqdl=new ArrayList<>();
    private ArrayList<String> Fxall=new ArrayList<>();
    private ArrayList<String> UM=new ArrayList<>();
    private ArrayList<String> TV360T=new ArrayList<>();
    private ArrayList<String> TVRET=new ArrayList<>();
    private ArrayList<String> TVBBG=new ArrayList<>();
    private ArrayList<String> TVSBP=new ArrayList<>();
    private ArrayList<String> FNC=new ArrayList<>();
    private HashMap<String,ArrayList<String>> FicherosOperativa = new HashMap<>();

    private int month;
    private int year;

    public UploadResults (){

        //EQC
        Eqc.add("1VhlVh-cihLyu23B7acI2lrkzea19W6z7OgC80BrD7oI");
        Eqc.add("vacio");
        Eqc.add("vacio");
        Eqc.add("19YCTp2b9tTzDe43nQru7eVLB4YWi0_51G31DLbtFNss");
        Eqc.add("10gyGrlbGBKTRvt3anzY4xEyFu6zfOHQe_M0R7cghTPk");
        Eqc.add("1UBRBF89DtAZpaZj05AbuD2GtWQp5ehjNmZ7xP76dJ34");

        //EQDL
        Eqdl.add("1ln4u9vmakbwkFdxF214VrbaaPtizCmxW0HQ5cseIBXA");
        Eqdl.add("1zGApKQbl4_21Gxx9sxvtV3okpYA9oVomldwtdALVJrM");
        Eqdl.add("15AV2IcPgrAOCF7d-QeKakSzqft20K2Q6eCZqGOxXe44");
        Eqdl.add("1n3QIrmcYzm71ifvoGvypklfXZau14qXEhlUqiQcLuXU");
        Eqdl.add("1bmNbgQnahG2dfADbxVtEAfLc_vhT3Hww24LPM7auqW8");
        Eqdl.add("1EdreY2JoUNqqgiZmoFsVBfKZQCfsJ8rAMCXzXngQSBI");

        //EQD 2019
        EqdFlow.add("vacio");
        EqdFlow.add("vacio");
        EqdFlow.add("vacio");
        EqdFlow.add("18GWdwCNiJMNKXsxyTmgUHEe6UpFcHNIdupBRtLpA4wo");
        EqdFlow.add("1e6JX4vI5nH5D_zCnhaUGjvh_VMRwLVkQx7xxxEI6L7c");
        EqdFlow.add("1kB9AgXft8nANKNX8CyIkGcSG0dDat7PF-Q1fTcG--vw");

        //FXALL
        Fxall.add ("vacio");//q1 2018
        Fxall.add ("vacio");//q2 2018
        Fxall.add ("vacio");//q3 2018
        Fxall.add("1nWlZ9WMyaTtr6UmQIHOC5P5BspX9nWuHiUsN9ngqL4o");//q4 2018
        Fxall.add("1hu2MLjsCXq5i3NEvZ-9Vq8-LRbJDmIxKy5xcpqGID6E");//q1 2019
        Fxall.add("1AaQUao0DP9NQqW2jA0B3X8RJjdRSeAHciCLrryADNOM");// q2 2019

        //UM
        UM.add("vacio");//q1 2018
        UM.add("vacio");//q2 2018
        UM.add("vacio");//q3 2018
        UM.add("1DLULSCeOfRs_mZwpbxJKAYY1PMfYfViNHSzJimJjcdo");//q4 2018
        UM.add("10smiYNm8cr6hZIUkDt-c33MZPSf08laKRc-zNlf7kzk");//q1 2019
        UM.add("12R3qXshXtze39DtGvpMVSw2yuXZRJHgWiFUONDPdGF4");//q2 2019

        //360T
        TV360T.add("vacio");//q1 2018
        TV360T.add("vacio");//q2 2018
        TV360T.add("vacio");//q3 2018
        TV360T.add("1TK02EBLGTno2KAcFeQARboQKFTVUgPjiiqGtsmLRUNQ");//q4 2018
        TV360T.add("1GvFZE3jSQp4Z2bU_8nPpikGOq1LolfPe2i0vR6jGBWY");//q1 2019
        TV360T.add("1xQuzuGUbAOvAjA8w1pjg6rlIJ_53tyafmY7DZeEMQRk");//q2 2019

        //RET
        TVRET.add("vacio");//q1 2018
        TVRET.add("vacio");//q2 2018
        TVRET.add("vacio");//q3 2018
        TVRET.add("18kDl4b99II4AeY7GH7VVsBPS5DZsdNB6N4GbID5GGCk");//q4 2018
        TVRET.add("1A7cC1mwV0jfvOM5ARw4kY_MCjTA9UPpAvgdCnWWHQ7Y");//q1 2019
        TVRET.add("1yETIsg0iwDT9-NxDk0SZ21eiMaO2kkfUsg20JmOfi-I");//q2 2019

        //BBG
        TVBBG.add("vacio");//q1 2018
        TVBBG.add("vacio");//q2 2018
        TVBBG.add("vacio");//q3 2018
        TVBBG.add("1bR4_zgpcI7rm3TvjC5_damlWJkuaGs-aWmFpBjYjrP8");//q4 2018
        TVBBG.add("1KqQ_z0vBNyo8zbY0xWA2ijBp9fsmyMGp7YpfPIryxzA");//q1 2019
        TVBBG.add("1SU951js9EyfIf9CPLifyG64G8y6v7HyImwPxGoJt8_Q");//q2 2019

        //SBP
        TVSBP.add("vacio");//q1 2018
        TVSBP.add("vacio");//q2 2018
        TVSBP.add("vacio");//q3 2018
        TVSBP.add("1n5eQodgnF8SywBi7pCeCdO0UoyH_oOVNqoozHK3WCaw");//q4 2018
        TVSBP.add("1XCfW-pkcsuCTGof9KXclYlybBdBR7YRt98ruSghm-WE");//q1 2019
        TVSBP.add("1McgDDMxRD7fbgtMgjMOQkbiRNk0cW5LucIwdBLMuK5Y");//q2 2019

        //FNC
        FNC.add("1Y71EC9n0hc1FGLRWntHwAahxwAu3Gu4NMm1yKiDPk9c");//q1 2018
        FNC.add("vacio");//q2 2018
        FNC.add("vacio");//q3 2018
        FNC.add("1WC1p1rMlBgz6IXLbS3mGndawBuLz1m20PIWA8KF4-PI");//q4 2018
        FNC.add("1_5pW_0w0gbhkqokWi23gd10bO14096o6FgJ5sa_HxUU");//q1 2019
        FNC.add("1bJJpQYnZjWcpECB2-mJgW20xePQiUjWnWLtoIw4E7rY");//q2 2019





        FicherosOperativa.put(EQC_BTCA_ORDER,Eqc);
        FicherosOperativa.put(EQC_BTCA_PLACEMENT,Eqc);
        FicherosOperativa.put(_FLOW,EqdFlow);
        FicherosOperativa.put(EQDL_BTCA_ORDER,Eqdl);
        FicherosOperativa.put(EQDL_BTCA_PLACEMENT,Eqdl);
        FicherosOperativa.put(FXALL,Fxall);
        FicherosOperativa.put(UM_BTCA_RFQ,UM);
        FicherosOperativa.put(T360T,TV360T);
        FicherosOperativa.put(RET,TVRET);
        FicherosOperativa.put(BBG,TVBBG);
        FicherosOperativa.put(SBP_BTCA_RFQ,TVSBP);
        FicherosOperativa.put(FNC_BTCA_RFQ,FNC);


    }


    private  String obtenerHojaQ(String fecha,String version){
        int valueQ;
        dateMaker(fecha);
        int mes = month;

        if (year==2019){
            if (mes<=3){
                valueQ=4;
            }else {
                valueQ=5;
            }
        }else {
            if (mes<=3){
                valueQ=0;
            }else if(mes<=6){
                valueQ=1;
            }else if(mes <= 9){
                valueQ=2;
            }else {
                valueQ=3;

            }
        }

        ArrayList<String> values= FicherosOperativa.get(version);
        System.out.println(version+" "+values.get(valueQ));
        return values.get(valueQ);
    }

    public void actualizarDatos(String version, String fecha,String Status, String value) throws IOException, GeneralSecurityException {
        ConectionSheets con = new ConectionSheets();
        con.actualizar(obtenerHojaQ(fecha,version),fecha,Status,value,version);
    }

    private void dateMaker(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        month = localDate.getMonthValue();
        year = localDate.getYear();
    }

}
