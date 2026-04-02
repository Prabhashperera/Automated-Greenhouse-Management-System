package com.project.zonemanagementservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "zones")
public class Zone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // e.g., "Tomato Zone" [cite: 28]
    private String name;
    private Double minTempo;
    private Double maxTempo;
    // This stores the ID returned from the External IoT API after registration [cite: 124]
    private String deviceId;
}
