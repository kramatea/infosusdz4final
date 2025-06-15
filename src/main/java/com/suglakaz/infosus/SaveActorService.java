package com.suglakaz.infosus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SaveActorService implements JavaDelegate{

	private static final Logger LOGGER = LoggerFactory.getLogger(SaveActorService.class);
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.info("saving actor information to database");
		
		
        
        String ime = (String)execution.getVariable("form_name");
		String prezime = (String)execution.getVariable("form_surname");
		String show = (String)execution.getVariable("form_selectshow");
		

        
        @SuppressWarnings("unchecked")
        List<Map<String, String>> actorList = (List<Map<String, String>>) execution.getVariable("savedActors");
        if (actorList == null) {
            actorList = new ArrayList<>();
        }
        
        Map<String, String> actorData = new HashMap<>();
        actorData.put("actorName", ime);
        actorData.put("actorSurname", prezime);
        actorData.put("actorShow", show);
        
        actorList.add(actorData);
        
        execution.setVariable("savedActors", actorList);
        execution.setVariable("actorSaved", true);
        
        LOGGER.info("Actor successfully saved to list. Total actors: {}", actorList.size());
        LOGGER.info("Saved actor data: {}", actorData);
	}
	
	

}
