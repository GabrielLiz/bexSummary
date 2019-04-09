package com.bex.btca.steps;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bex.btca.model.EstadisticasTrade;
import com.bex.btca.model.Totales;

@Component
public class TaskletStep implements Tasklet {
	// EQC
	public String EQC_BTCA_ORDER = "EQC_BTCA_ORDER";
	public String EQC_BTCA_PLACEMENT = "EQC_BTCA_PLACEMENT";

	// FX
	public String T360T = "360T";
	public String FXALL = "FXALL";
	public String RET = "RET";
	public String BBG = "BBG";
	public String UM_BTCA_RFQ = "UM_BTCA_RFQ";
	public String FNC_BTCA_RFQ = "FNC_BTCA_RFQ";

	// EQD
	public String _FLOW = "_FLOW";

	// EQDL
	public String EQDL_BTCA_ORDER = "EQDL_BTCA_ORDER";
	public String EQDL_BTCA_PLACEMENT = "EQDL_BTCA_PLACEMENT";

	// SBP
	public String SBP_BTCA_RFQ = "SBP_BTCA_RFQ";

	private StringBuilder resultadoRFQ;

	private JdbcTemplate jdbctemplate;
	ArrayList<String> listaRFQ;
	private String fileName;

	public TaskletStep(DataSource data) {
		super();
		this.jdbctemplate = new JdbcTemplate(data);


	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		 resultadoRFQ= new StringBuilder();
		 fileName();
		
		String sql = "SELECT * FROM trade";
		List<EstadisticasTrade> fulldata = jdbctemplate.query(sql, new BeanPropertyRowMapper<EstadisticasTrade>(EstadisticasTrade.class));
	

		List<String> fechaOpe = jdbctemplate.queryForList("SELECT DISTINCT version from rfq", String.class);
	//	List<String> horadesbida = jdbctemplate.queryForList("SELECT DISTINCT sent from btca  WHERE version=''", String.class);
	//List<String> fechaOpeTrade = jdbctemplate.queryForList("SELECT DISTINCT fecha_operativa from btca WHERE version=''", String.class);

		
		for (String stringst : fechaOpe) {
			int i=jdbctemplate.queryForObject("SELECT COUNT(version) FROM rfq WHERE version='"+stringst+"'", Integer.class);
				resultadoRFQ.append(stringst+";"+i +System.getProperty("line.separator"));
		}

		//	resul.append(Trades(horadesbida, fechaOpeTrade));

		writeRFQ(resultadoRFQ);
		writeTrade(fulldata);
		return null;
	}

	public void fileName() {
		File[] files = new File("/CSV/").listFiles();
		String valu = files[0].getName();
		fileName= valu.split("_")[0];
		
	}
	public void writeRFQ(StringBuilder dato) {
		File file = new File("/CSV/"+fileName+"_rfq.csv");

		FileWriter fr = null;
		BufferedWriter br = null;
		try {
			fr = new FileWriter(file);
			br = new BufferedWriter(fr);
			br.write("Plataforma;Status;fecha de operativa;total" + System.getProperty("line.separator"));
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
	
	public void writeTrade(	List<EstadisticasTrade> dato) {
		File file = new File("/CSV/"+fileName+"_Trade.csv");

		FileWriter fr = null;
		BufferedWriter br = null;
		try {
			fr = new FileWriter(file);
			br = new BufferedWriter(fr);
			br.write("Status;fecha de operativa;asset;posiblemente;subido;total" + System.getProperty("line.separator"));
			for (EstadisticasTrade estadisticasTrade : dato) {
				br.write(estadisticasTrade.getBusqueda()+";"+Integer.toString(estadisticasTrade.getValores())+ System.getProperty("line.separator"));
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

	public StringBuilder RFQs(List<String> fecha_operativa, String version) {
		
		StringBuilder resultado = new StringBuilder();
		resultado.append("Las RFQ de " + version + " \n");

		for (String fecha : fecha_operativa) {
			String SQL_ACEPTED = "SELECT COUNT (version) FROM btca WHERE status='Accepted' AND fecha_operativa ='"+ fecha + "' AND version LIKE '%" + version + "%'";
			String SQL_REJECTED = "SELECT COUNT (version) FROM btca WHERE status='Rejected' AND fecha_operativa ='"+ fecha + "' AND version LIKE '%" + version + "%'";
			String SQL_SENT = "SELECT COUNT (version) FROM btca WHERE status='Sent' AND fecha_operativa ='" + fecha+ "' AND version LIKE '%" + version + "%'";

			int accept = jdbctemplate.queryForObject(SQL_ACEPTED, Integer.class);
			int rejected = jdbctemplate.queryForObject(SQL_REJECTED, Integer.class);
			int sent = jdbctemplate.queryForObject(SQL_SENT, Integer.class);

			if (accept > 0 || rejected > 0 || sent > 0) {
				resultado.append(fecha+System.getProperty("line.separator"));
				resultado.append("Status; number; asset"+ System.getProperty("line.separator"));
				resultado.append("Aceptado;"+accept + ";"+ System.getProperty("line.separator"));
				resultado.append("Rejected;"+rejected + ";"+ System.getProperty("line.separator"));
				resultado.append("Sent;"+sent + ";"+ System.getProperty("line.separator"));
				resultado.append("Total;=SUMA() "+ System.getProperty("line.separator"));

			}
		}

		return resultado;

	}

	public StringBuilder Trades(List<String> HoraDeSubida, List<String> fecha_operativa) {
		StringBuilder resultadoTrade=new StringBuilder();
		String fecha=null;
		String hora=null;
		resultadoTrade.append("****************************TRADES*********************************");
		resultadoTrade.append(System.getProperty("line.separator"));

		for (int i=0;i<fecha_operativa.size();i++) {
			fecha=fecha_operativa.get(i);

			for (int e=0;e<HoraDeSubida.size();e++) {
				hora=HoraDeSubida.get(e);

				
				String  SQL_ACEPTED = "SELECT COUNT (version) FROM btca WHERE status='Accepted' AND fecha_operativa ='"+fecha+"' AND version ='' AND sent ='"+hora+"'";
				String  SQL_REJECTED = "SELECT COUNT (version) FROM btca WHERE status='Rejected' AND fecha_operativa ='"+fecha+"' AND version ='' AND sent ='"+hora+"'";
				String  SQL_SENT = "SELECT COUNT (version) FROM btca WHERE status='Sent' AND fecha_operativa ='"+fecha+"' AND version ='' AND sent ='"+hora+"'";

				int accept =	jdbctemplate.queryForObject(SQL_ACEPTED, Integer.class);
				int rejected =	jdbctemplate.queryForObject(SQL_REJECTED, Integer.class);
				int sent =	jdbctemplate.queryForObject(SQL_SENT, Integer.class);
				
				if (accept>0||rejected>0||sent>0) {
					resultadoTrade.append(fecha+" "+hora+System.getProperty("line.separator"));
					resultadoTrade.append("Aceptados;"+accept+System.getProperty("line.separator"));
					resultadoTrade.append("Rejected;"+rejected+System.getProperty("line.separator"));
					resultadoTrade.append("Sent;"+sent+System.getProperty("line.separator"));
					resultadoTrade.append("Total;=SUMA() "+ System.getProperty("line.separator"));


				}
			
			}
			
		}
		return resultadoTrade;

	}
}