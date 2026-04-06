package com.project.zonemanagementservice.controller;

import com.project.zonemanagementservice.entity.Zone;
import com.project.zonemanagementservice.service.ZoneService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
@AllArgsConstructor
public class ZoneController {
    private final ZoneService zoneService;

    @PostMapping
    public ResponseEntity<Zone> createZone(@RequestBody Zone zoneData) {
        return ResponseEntity.ok(zoneService.createZone(zoneData));
    }

    @GetMapping
    public ResponseEntity<List<Zone>> getAllZones() {
        return ResponseEntity.ok(zoneService.getAllZones());
    }
}