package com.project.automationservice.client;

import com.project.automationservice.dto.ZoneDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "zone-service")
public interface ZoneClient {
    // You'll need to add this endpoint to your ZoneController (8081)
    @GetMapping("/api/zones/name/{name}")
    ZoneDTO getZoneByName(@PathVariable("name") String name);

    // Inside com.project.automationservice.client.ZoneClient
    @GetMapping("/api/zones/device/{deviceId}")
    ZoneDTO getZoneByDeviceId(@PathVariable("deviceId") String deviceId);
}
