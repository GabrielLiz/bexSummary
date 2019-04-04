package com.bex.btca.listener;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bex.btca.model.Totales;

@Component
public class JobListener extends JobExecutionListenerSupport{
	
	private static final Logger LOG = LoggerFactory.getLogger(JobListener.class);
	
	private JdbcTemplate jdbctemplate;

	@Autowired
	public JobListener(JdbcTemplate jdbctemplate) {
		super();
	//	this.jdbctemplate = jdbctemplate;
	}
	
	
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		super.afterJob(jobExecution);
		if(jobExecution.getStatus()==BatchStatus.COMPLETED) {
			
			
	
		}
	}

}
