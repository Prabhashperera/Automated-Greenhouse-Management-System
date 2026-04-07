package com.project.cropinventoryservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// src/main/java/com/project/cropinventoryservice/entity/CropBatch.java
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CropBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cropType; // e.g., "Tomato"
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private CropStatus status; // SEEDLING, VEGETATIVE, or HARVESTED

    private LocalDateTime plantedDate;
}
