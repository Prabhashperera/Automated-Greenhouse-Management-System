package com.project.automationservice.service;

import com.project.automationservice.client.ZoneClient;
import com.project.automationservice.dto.SensorDataDTO;
import com.project.automationservice.dto.ZoneDTO;
import com.project.automationservice.entity.AutomationAction;
import com.project.automationservice.repo.AutomationRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class AutomationService {
    private final ZoneClient zoneClient; // Client to talk to Port 8081
    @Autowired
    private AutomationRepo automationRepo;

    public AutomationService(ZoneClient zoneClient) {
        this.zoneClient = zoneClient;
    }

    public void processData(SensorDataDTO data) {
        try {
            // Fetch thresholds
            ZoneDTO zone = zoneClient.getZoneByName(data.getZoneId());
            if (zone == null) return;

            double currentTemp = data.getValue().getTemperature();
            String actionTaken = null;

            if (currentTemp > zone.getMaxTemp()) {
                actionTaken = "TURN_FAN_ON";
            } else if (currentTemp < zone.getMinTemp()) {
                actionTaken = "TURN_HEATER_ON";
            }

            if (actionTaken != null) {
                // Save to Database
                AutomationAction logEntry = AutomationAction.builder()
                        .zoneName(zone.getName())
                        .action(actionTaken)
                        .capturedValue(currentTemp)
                        .timeStamps(LocalDateTime.now())
                        .build();

                automationRepo.save(logEntry);
                log.info("Saved Action: {} for Zone: {}", actionTaken, zone.getName());
            }
        } catch (Exception e) {
            System.err.println("Automation Logic Error: " + e.getMessage());
            // This prevents the 500 error from propagating back to Telemetry
        }
    }
}
