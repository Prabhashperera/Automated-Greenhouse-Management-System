package com.project.telemetryservice.controller;

import com.project.telemetryservice.dto.SensorDataDTO;
import com.project.telemetryservice.service.TelemetryBackgroundService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {
    private final TelemetryBackgroundService telemetryService;

    public SensorController(TelemetryBackgroundService telemetryService) {
        this.telemetryService = telemetryService;
    }

    @GetMapping("/latest")
    public ResponseEntity<SensorDataDTO> getLatestReading() {
        return ResponseEntity.ok(telemetryService.getLatest());
    }
}
