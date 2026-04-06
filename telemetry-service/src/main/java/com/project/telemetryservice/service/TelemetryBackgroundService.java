package com.project.telemetryservice.service;

import com.project.telemetryservice.client.AutomationClient;
import com.project.telemetryservice.client.IotTelemetryClient;
import com.project.telemetryservice.dto.SensorDataDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@Slf4j
public class TelemetryBackgroundService {

    private final IotTelemetryClient iotClient;
    private final AutomationClient automationClient;
    private SensorDataDTO lastReading; // For the /latest endpoint

    public TelemetryBackgroundService(IotTelemetryClient iotClient, AutomationClient automationClient) {
        this.iotClient = iotClient;
        this.automationClient = automationClient;
    }

    @Scheduled(fixedRate = 10000) // Runs every 10 seconds
    public void fetchAndPushTelemetry() {
        try {
            // Note: In a full system, you would get all device IDs from the Zone Service
            // For now, use a test device ID you registered earlier
            String testDeviceId = "YOUR_REGISTERED_DEVICE_ID";

            // 1. THE FETCHER: Call IoT API
            SensorDataDTO data = iotClient.getTelemetry(testDeviceId);
            this.lastReading = data;
            log.info("Fetched data: {}°C from device {}", data.getValue().getTemperature(), testDeviceId);

            // 2. THE PUSHER: Send to Automation Service
            automationClient.sendToAutomation(data);

        } catch (Exception e) {
            log.error("Telemetry bridge failure: {}", e.getMessage());
        }
    }

    public SensorDataDTO getLatest() {
        return lastReading;
    }
}
