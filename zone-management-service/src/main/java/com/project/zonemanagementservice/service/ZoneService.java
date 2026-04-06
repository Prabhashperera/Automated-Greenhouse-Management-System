package com.project.zonemanagementservice.service;

import com.project.zonemanagementservice.client.ExternalIotClient;
import com.project.zonemanagementservice.entity.Zone;
import com.project.zonemanagementservice.repo.ZoneRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ZoneService {
    private final ZoneRepo zoneRepo;
    private final ExternalIotClient externalIotClient;

    public Zone createZone(Zone zone) {
        // Business Rule: Validate min < max [cite: 212]
        if (zone.getMinTemp() >= zone.getMaxTemp()) {
            throw new RuntimeException("Validation Failed: Min Temp must be less than Max Temp.");
        }

        // Prepare data for IoT Service [cite: 167-171]
        Map<String, Object> request = new HashMap<>();
        request.put("name", zone.getName() + "-Sensor");
        request.put("zoneId", zone.getName());

        try {
            // The FeignConfig will automatically add the Token header here
            Map<String, Object> response = externalIotClient.registerDevice(request);

            // Capture the returned deviceId [cite: 177, 213]
            if (response != null && response.containsKey("deviceId")) {
                zone.setDeviceId((String) response.get("deviceId"));
            }
        } catch (Exception e) {
            System.err.println("IoT Registration Failed: " + e.getMessage());
            // Important: Don't save the zone if the device registration fails
            throw new RuntimeException("Failed to register device with IoT Service: " + e.getMessage());
        }

        return zoneRepo.save(zone);
    }

    // Inside ZoneService.java (8081)
    public List<Zone> getAllZones() {
        return zoneRepo.findAll();
    }
}