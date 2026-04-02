package com.project.zonemanagementservice.service;


import com.project.zonemanagementservice.client.ExternalIotClient;
import com.project.zonemanagementservice.entity.Zone;
import com.project.zonemanagementservice.repo.ZoneRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class ZoneService {
    private final ZoneRepo zoneRepo;
    private final ExternalIotClient externalIotClient;

    public Zone createZone(Zone zone) {
        if (zone.getMinTempo() >= zone.getMaxTempo()) {
            throw new RuntimeException("Validation Failed: Minimum temperature must be less than Maximum temperature.");
        }
        //Prepare the data Packet
        Map<String, Object> request = new HashMap<>();
        request.put("name", zone.getName() + "-Device");

        try {
            Map<String, Object> response = externalIotClient.registerDevice(request);
            String generatedID = response.get("deviceId").toString();
            zone.setDeviceId(generatedID);
        } catch (Exception e) {
            throw new RuntimeException("External IoT API is unreachable. Cannot register device.");
        }
        return zoneRepo.save(zone);
    }

}
