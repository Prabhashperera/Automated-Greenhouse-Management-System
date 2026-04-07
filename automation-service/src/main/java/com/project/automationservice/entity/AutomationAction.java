package com.project.automationservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "automation_actions")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AutomationAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String zoneName;
    private String action; // e.g., "TURN_FAN_ON"
    private Double capturedValue; // The temp that triggered the action
    @CurrentTimestamp
    @Column(updatable = false)
    private LocalDateTime timeStamps;
}
