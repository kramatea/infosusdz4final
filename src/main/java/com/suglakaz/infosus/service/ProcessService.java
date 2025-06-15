// ProcessService.java
package com.suglakaz.infosus.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessService {

    @Autowired
    private RuntimeService runtimeService;


    public String startActorProcess() {
        Map<String, Object> variables = new HashMap<>();
       
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
            "proces-process", 
            "busy", 
            variables
        );

        return processInstance.getId();
    }

    public List<Map<String, Object>> getActiveProcesses() {
        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery()
            .active()
            .list();

        List<Map<String, Object>> processes = new ArrayList<>();
        
        for (ProcessInstance pi : processInstances) {
            Map<String, Object> processData = new HashMap<>();
            processData.put("id", pi.getId());
            processData.put("businessKey", pi.getBusinessKey());
            processData.put("processDefinitionKey", pi.getProcessDefinitionKey());
            
            Map<String, Object> variables = runtimeService.getVariables(pi.getId());
            processData.put("variables", variables);
            
            processes.add(processData);
        }
        
        return processes;
    }

    public void sendBackgroundCheckResult(String processInstanceId, String status) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("backgroundCheckStatus", status);
        variables.put("checkTime", new Date());

        runtimeService.createMessageCorrelation("BackgroundCheckResult")
            .processInstanceId(processInstanceId)
            .setVariables(variables)
            .correlate();
    }
}
