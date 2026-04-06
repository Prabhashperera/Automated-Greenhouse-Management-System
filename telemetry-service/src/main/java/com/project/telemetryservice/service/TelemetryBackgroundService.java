package com.project.telemetryservice.service;

import com.project.telemetryservice.client.AutomationClient;
import com.project.telemetryservice.client.IotTelemetryClient;
import com.project.telemetryservice.client.ZoneClient;
import com.project.telemetryservice.dto.SensorDataDTO;
import com.project.telemetryservice.dto.ZoneDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
@Slf4j
public class TelemetryBackgroundService {
    @Autowired
    private ZoneClient zoneClient;
    private final IotTelemetryClient iotClient;
    private final AutomationClient automationClient;
    private SensorDataDTO lastReading; // For the /latest endpoint

    public TelemetryBackgroundService(IotTelemetryClient iotClient, AutomationClient automationClient) {
        this.iotClient = iotClient;
        this.automationClient = automationClient;
    }

    @Scheduled(fixedRate = 10000)
    public void fetchAndPushTelemetry() {
        try {
            // 1. Automatically get all zones from the Zone Service
            List<ZoneDTO> zones = zoneClient.getAllZones();

            if (zones == null || zones.isEmpty()) {
                log.warn("No zones found. Create a zone in Port 8081 first!");
                return;
            }

            // 2. Loop through every zone/device
            for (ZoneDTO zone : zones) {
                String activeId = zone.getDeviceId();

                if (activeId != null) {
                    // 3. Fetch telemetry using the REAL ID from the database
                    SensorDataDTO data = iotClient.getTelemetry(activeId);

                    if (data != null) {
                        this.lastReading = data;
                        // 4. Push to Automation Service
                        automationClient.sendToAutomation(data);
                        log.info("Successfully processed device: {}", activeId);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Automatic Telemetry failed: {}", e.getMessage());
        }
    }

    public SensorDataDTO getLatest() {
        return lastReading;
    }
}
