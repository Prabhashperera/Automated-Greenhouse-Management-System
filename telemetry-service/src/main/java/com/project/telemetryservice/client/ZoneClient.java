package com.project.telemetryservice.client;

import com.project.telemetryservice.dto.ZoneDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "zone-service") // Eureka looks up 'ZONE-SERVICE'
public interface ZoneClient {
    @GetMapping("/api/zones")
    List<ZoneDTO> getAllZones();
}