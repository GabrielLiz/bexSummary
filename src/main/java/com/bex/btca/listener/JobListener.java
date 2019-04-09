package com.bex.btca.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bex.btca.Window;

@Component
public class JobListener implements JobExecutionListener{
	
	private static final Logger LOG = LoggerFactory.getLogger(JobListener.class);
	
	private JdbcTemplate jdbctemplate;

	@Autowired
	public JobListener(JdbcTemplate jdbctemplate) {
		super();
	}
	
	
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus()==BatchStatus.FAILED) {
			Window.text.setText(Window.text.getText()+ "\n " +"Ha fallado pongase en contacto con el tecnico");

		}
		if(jobExecution.getStatus()==BatchStatus.COMPLETED) {
			Window.text.setText(Window.text.getText()+ "\n " +"Finalizado");
			Window.ejecutar.setEnabled(true);
		}
		if(jobExecution.getStatus()==BatchStatus.STARTING) {
			Window.text.setText("Analizando....");
		}
	}



	@Override
	public void beforeJob(JobExecution jobExecution) {
		if(jobExecution.getStatus()==BatchStatus.STARTED) {
			Window.text.setText(" Iniciando...");
		}
	

	}

}
