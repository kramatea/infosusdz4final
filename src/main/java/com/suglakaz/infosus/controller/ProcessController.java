// ProcessController.java
package com.suglakaz.infosus.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.suglakaz.infosus.service.ProcessService;

@Controller
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @GetMapping("/home")
    public String index(Model model) {
        model.addAttribute("appTitle", "Actor Management System");
        return "index";
    }

    @PostMapping("/start-process")
    public String startProcess(RedirectAttributes redirectAttributes) {
        try {
            String processInstanceId = processService.startActorProcess();
            redirectAttributes.addFlashAttribute("success", 
                "Process started successfully! ID: " + processInstanceId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Failed to start process: " + e.getMessage());
        }
        return "redirect:/home";
    }

    @GetMapping("/api/processes")
    @ResponseBody
    public Map<String, Object> getProcessesApi() {
        return Map.of(
            "processes", processService.getActiveProcesses(),
            "count", processService.getActiveProcesses().size()
        );
    }
}
