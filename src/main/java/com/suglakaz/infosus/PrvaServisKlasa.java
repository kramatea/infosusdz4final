package com.suglakaz.infosus;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PrvaServisKlasa implements JavaDelegate{

	private static final Logger LOGGER = LoggerFactory.getLogger(PrvaServisKlasa.class);
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.info("moj prvi servis");
		Object variable = execution.getVariable("prvaVar");
		LOGGER.info((String) variable);
	}
	
	

}
