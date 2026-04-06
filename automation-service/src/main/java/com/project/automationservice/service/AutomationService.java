package com.project.automationservice.service;

import com.project.automationservice.client.ZoneClient;
import com.project.automationservice.dto.SensorDataDTO;
import com.project.automationservice.dto.ZoneDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AutomationService {
    private final ZoneClient zoneClient; // Client to talk to Port 8081

    public AutomationService(ZoneClient zoneClient) {
        this.zoneClient = zoneClient;
    }

    public void processData(SensorDataDTO data) {
        try {
            // Fetch thresholds
            ZoneDTO zone = zoneClient.getZoneByName(data.getZoneId());

            if (zone == null) {
                System.err.println("Skipping: No zone found with name " + data.getZoneId());
                return; // Stop here instead of crashing
            }

            double currentTemp = data.getValue().getTemperature();

            // Execute Rules
            if (currentTemp > zone.getMaxTemp()) {
                System.out.println("!!! ALERT: TURN_FAN_ON in " + zone.getName());
            } else if (currentTemp < zone.getMinTemp()) {
                System.out.println("!!! ALERT: TURN_HEATER_ON in " + zone.getName());
            }
        } catch (Exception e) {
            System.err.println("Automation Logic Error: " + e.getMessage());
            // This prevents the 500 error from propagating back to Telemetry
        }
    }
}
