package com.bex.btca.steps;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.sql.DataSource;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bex.btca.Window;
import com.bex.btca.model.EstadisticasTrade;
import com.bex.btca.utils.UploadResults;

@Component
public class TaskletStep implements Tasklet {

    private StringBuilder resultadoRFQ;

    private JdbcTemplate jdbctemplate;
    private String fileName;

    public TaskletStep(DataSource data) {
        super();
        this.jdbctemplate = new JdbcTemplate(data);

    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        resultadoRFQ = new StringBuilder();
        fileName();

        String sql = "SELECT * FROM trade";
        List<EstadisticasTrade> fulldata = jdbctemplate.query(sql,
                new BeanPropertyRowMapper<EstadisticasTrade>(EstadisticasTrade.class));

        List<String> fechaOpe = jdbctemplate.queryForList("SELECT DISTINCT version from rfq", String.class);
        // List<String> horadesbida = jdbctemplate.queryForList("SELECT DISTINCT sent
        // from btca WHERE version=''", String.class);
        // List<String> fechaOpeTrade = jdbctemplate.queryForList("SELECT DISTINCT
        // fecha_operativa from btca WHERE version=''", String.class);

        UploadResults results = new UploadResults();
        if (Window.subir) {
            Window.text.setText(Window.text.getText() + "\n " + "Los siguientes valores se escriben automaticamente en los ficheros de Drive");
        }
        for (String stringst : fechaOpe) {
            int i = jdbctemplate.queryForObject("SELECT COUNT(version) FROM rfq WHERE version='" + stringst + "'",
                    Integer.class);
            StringTokenizer datos = new StringTokenizer(stringst, ";");
            StringBuilder valappend = new StringBuilder();
            List<String> elements = new ArrayList<>();

            // iterate through StringTokenizer tokens
            while (datos.hasMoreTokens()) {
                elements.add(datos.nextToken());
            }
            if (Window.subir) {
                try {
                    results.actualizarDatos(elements.get(0), elements.get(2), elements.get(1), i + "");
                    Window.text.setText(Window.text.getText() + "\n " + elements.get(0) + " " + elements.get(1) + " " + i + " " + elements.get(2));

                } catch (Exception e) {
                    Window.text.setText(Window.text.getText() + "\n " + "Fallo de escritura para 	" + elements.get(0) + " " + elements.get(1) + " " + i + " " + elements.get(2));

                }
            }
            valappend.append(elements.get(2));
            valappend.append(elements.get(0));
            valappend.append(elements.get(1));


            resultadoRFQ.append(stringst + ";" + valappend + ";" + i + System.getProperty("line.separator"));
        }

        // resul.append(Trades(horadesbida, fechaOpeTrade));

        if (Window.rfqs) {
            writeRFQ(resultadoRFQ);
        }
        if (Window.trade) {
            writeTrade(fulldata);
        }

        logWriter("",Window.text.getText().toString());
        return null;
    }

    public void fileName() {
        File[] files = new File("/CSV/").listFiles();
        for (int i = 0; i < files.length; i++) {
            String[] values = files[i].getName().split("_");
            if (values.length == 2) {
                if (values[1].equals("total.csv")) {
                    fileName = values[0];
                }
            }
        }

    }

    public void writeRFQ(StringBuilder dato) {
        File file = new File("/CSV/" + fileName + "_rfq.csv");

        FileWriter fr = null;
        BufferedWriter br = null;
        try {
            StringBuilder valappend = new StringBuilder();

            fr = new FileWriter(file);
            br = new BufferedWriter(fr);
            br.write("Plataforma;Status;File Date;Contatenado;total" + System.getProperty("line.separator"));
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

    public void writeTrade(List<EstadisticasTrade> dato) {
        File file = new File("/CSV/" + fileName + "_Trade.csv");

        FileWriter fr = null;
        BufferedWriter br = null;
        try {
            fr = new FileWriter(file);
            br = new BufferedWriter(fr);
            br.write(
                    "Status;fecha de operativa;asset;posiblemente;subido;total" + System.getProperty("line.separator"));
            for (EstadisticasTrade estadisticasTrade : dato) {
                br.write(estadisticasTrade.getBusqueda() + ";" + Integer.toString(estadisticasTrade.getValores())
                        + System.getProperty("line.separator"));
            }
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



    public void logWriter(String date, String log) throws IOException {
        FileWriter fileWriter = new FileWriter("c:/CSV/log.txt", true); //Set true for append mode
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(log);  //New line
        printWriter.close();

    }

}