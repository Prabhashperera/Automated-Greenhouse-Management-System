package com.project.automationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZoneDTO {
    // Matches the name field in your Zone Entity [cite: 28]
    private String name;

    // The threshold values the Automation Service needs for its rules [cite: 145-146]
    private Double minTemp;
    private Double maxTemp;

    // The unique ID assigned to the hardware [cite: 124]
    private String deviceId;
}