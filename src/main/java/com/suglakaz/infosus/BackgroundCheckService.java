package com.suglakaz.infosus;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BackgroundCheckService implements JavaDelegate{

	private static final Logger LOGGER = LoggerFactory.getLogger(BackgroundCheckService.class);
    private static final Random random = new Random();
	
    @Autowired
    private RuntimeService runtimeService;
    
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.info("background check of your actor provided by MUP");
		
		String ime = (String)execution.getVariable("firstName");
		String prezime = (String)execution.getVariable("lastName");
		
		double rand = random.nextDouble();
		 boolean isOk = rand < 0.8;
	     boolean status = isOk ? true : false;
	     
			LOGGER.info("random broj mi je "+ rand);

		
		execution.setVariable("backgroundCheckStatus", status);
		LOGGER.info("Background check result: {}", status);
		
        String targetProcessInstanceId = "proces-process";		
        
        Map<String, Object> messageVariables = new HashMap<>();
        messageVariables.put("firstName", ime);
        messageVariables.put("lastName", prezime);
        messageVariables.put("checkStatus", status);
        
        String businessKey = (String) "busy";
        
		 try {
	            runtimeService.createMessageCorrelation("msg_result") // Global Message Ref
	                //.processInstanceId(targetProcessInstanceId)                   // ne radi
	            	.processInstanceBusinessKey(businessKey)
	            	.processInstanceVariableEquals("form_name", ime)
	                .setVariables(messageVariables)                              
	                .correlate();                                                
	            
	           // LOGGER.info("Message successfully sent to process: {}", targetProcessInstanceId);
	            LOGGER.info("Variables sent: {}", messageVariables);
	            
	        } catch (Exception e) {
	            LOGGER.error("Failed to send message: {}", e.getMessage());
	            throw e;
	        }
	}
	
	

}
