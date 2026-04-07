package com.project.automationservice.controller;

import com.project.automationservice.dto.SensorDataDTO;
import com.project.automationservice.entity.AutomationAction;
import com.project.automationservice.repo.AutomationRepo;
import com.project.automationservice.service.AutomationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/automation")
@AllArgsConstructor
public class AutomationController {
    private final AutomationService automationService;
    private final AutomationRepo automationRepo;

    @PostMapping("/process")
    public void receiveTelemetry(@RequestBody SensorDataDTO data) {
        automationService.processData(data);
    }

    // Add this to your existing AutomationController
    @GetMapping("/history")
    public ResponseEntity<List<AutomationAction>> getHistory() {
        return ResponseEntity.ok(automationRepo.findAll());
    }
}
