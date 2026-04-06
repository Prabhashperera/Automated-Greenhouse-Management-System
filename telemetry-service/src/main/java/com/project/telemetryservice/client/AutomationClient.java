package com.project.telemetryservice.client;

import com.project.telemetryservice.dto.SensorDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "automation-service") // Uses Eureka to find the service
public interface AutomationClient {
    @PostMapping("/api/automation/process")
    void sendToAutomation(@RequestBody SensorDataDTO data);
}
