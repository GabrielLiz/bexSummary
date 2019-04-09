package com.bex.btca.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bex.btca.Window;

@Component
public class StepListener implements StepExecutionListener {

	
	@Autowired
	public StepListener() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		
		String ecri=null;
		if(stepExecution.getStepName().equals("carga")) {
			ecri="Cargando el fichero en la BBDD temporal de C:/CSV/";
		}
		if(stepExecution.getStepName().equals("RFQs")) {
			ecri="Procesando RFQs";
		}
		if(stepExecution.getStepName().equals("PostTrade")) {
			ecri="Procesando Trades";
		}
		if(stepExecution.getStepName().equals("escribiendo")) {
			ecri="Generando ficheros en C:/CSV";
		}
		
		Window.text.setText(Window.text.getText()+ "\n " +ecri);
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {

		return null;
	}

}
