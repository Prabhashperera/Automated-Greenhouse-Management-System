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
        // 1. Fetch thresholds from Zone Service for this specific zone
        // Business Logic: Threshold Fetching [cite: 144]
        ZoneDTO zone = zoneClient.getZoneByName(data.getZoneId());

        if (zone == null) {
            log.warn("No threshold data found for zone: {}", data.getZoneId());
            return;
        }

        double currentTemp = data.getValue().getTemperature();

        // 2. Execute Rules [cite: 145-146]
        if (currentTemp > zone.getMaxTemp()) {
            log.info("!!! ACTION REQUIRED in {}: Current Temp {}°C > Max {}°C. TURN_FAN_ON",
                    zone.getName(), currentTemp, zone.getMaxTemp());
        } else if (currentTemp < zone.getMinTemp()) {
            log.info("!!! ACTION REQUIRED in {}: Current Temp {}°C < Min {}°C. TURN_HEATER_ON",
                    zone.getName(), currentTemp, zone.getMinTemp());
        } else {
            log.info("Environment in {} is optimal ({}°C)", zone.getName(), currentTemp);
        }
    }
}
