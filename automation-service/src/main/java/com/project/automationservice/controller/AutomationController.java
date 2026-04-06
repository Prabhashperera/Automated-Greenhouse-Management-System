package com.project.automationservice.controller;

import com.project.automationservice.dto.SensorDataDTO;
import com.project.automationservice.service.AutomationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/automation")
public class AutomationController {
    private final AutomationService automationService;

    public AutomationController(AutomationService automationService) {
        this.automationService = automationService;
    }

    @PostMapping("/process")
    public void receiveTelemetry(@RequestBody SensorDataDTO data) {
        automationService.processData(data);
    }
}
