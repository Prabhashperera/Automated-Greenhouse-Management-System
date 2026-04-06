package com.project.telemetryservice.dto;

import lombok.Data;

@Data
public class ZoneDTO {
    private String name;
    private String deviceId; // This is the ID we need!
}
