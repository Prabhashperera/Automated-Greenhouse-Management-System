package com.project.zonemanagementservice.controller;

import com.project.zonemanagementservice.entity.Zone;
import com.project.zonemanagementservice.service.ZoneService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/zones")
@AllArgsConstructor
public class ZoneController {
    private final ZoneService zoneService;

    @PostMapping
    public ResponseEntity<Zone> createZone(@RequestBody Zone zoneData) {
        return ResponseEntity.ok(zoneService.createZone(zoneData));
    }
}