package com.suglakaz.infosus;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendDataService implements JavaDelegate{

	private static final Logger LOGGER = LoggerFactory.getLogger(SendDataService.class);
	
	 @Autowired
	    private RuntimeService runtimeService;
	 
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.info("sending actor data to background check service");

		String ime = (String)execution.getVariable("form_name");
		String prezime = (String)execution.getVariable("form_surname");

        Map<String, Object> messageVariables = new HashMap<>();
        messageVariables.put("firstName", ime);
        messageVariables.put("lastName", prezime);
        
        String targetProcessInstanceId = "process_mup";
        
        try {
            runtimeService.createMessageCorrelation("msg_check") //Global Message Reference
                .processInstanceId(targetProcessInstanceId)                   
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
