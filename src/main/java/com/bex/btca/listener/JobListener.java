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
public class JobListener implements JobExecutionListener {

	private static final Logger LOG = LoggerFactory.getLogger(JobListener.class);

	@Autowired
	public JobListener(JdbcTemplate jdbctemplate) {
		super();

	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		String value = null;

		if (jobExecution.getStatus() == BatchStatus.FAILED) {
			value = "\n " + "Ha fallado pongase en contacto con el tecnico";
		}
		if (jobExecution.getStatus() == BatchStatus.UNKNOWN) {
			value = "\n " + "Ha fallado pongase en contacto con el tecnico";
		}
		if (jobExecution.getStatus() == BatchStatus.ABANDONED) {
			value = "\n " + "Ha fallado pongase en contacto con el tecnico";
		}
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			value= "\n " + "Finalizado" + "\n Cerrar la ventana antes de volver a ejecutar";
			Window.ejecutar.setEnabled(true);
		}
		if (jobExecution.getStatus() == BatchStatus.STARTING) {
			value = "Analizando....";
		}

		Window.text.setText(Window.text.getText() + "\n " + value);
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		
		jobExecution.getExecutionContext();
		if (jobExecution.getStatus() == BatchStatus.STARTED) {
			Window.text.setText(" Iniciando...");
		}

	}

}
