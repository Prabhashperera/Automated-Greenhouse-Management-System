package com.project.cropinventoryservice.controller;

import com.project.cropinventoryservice.entity.CropBatch;
import com.project.cropinventoryservice.entity.CropStatus;
import com.project.cropinventoryservice.repo.CropRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

// src/main/java/com/project/cropinventoryservice/controller/CropController.java
@RestController
@RequestMapping("/api/crops")
@RequiredArgsConstructor
public class CropController {
    private final CropRepo cropRepo;

    // 1. Register new batch [cite: 158]
    @PostMapping
    public ResponseEntity<CropBatch> registerBatch(@RequestBody CropBatch batch) {
        batch.setStatus(CropStatus.SEEDLING); // Initial state
        batch.setPlantedDate(LocalDateTime.now());
        return ResponseEntity.ok(cropRepo.save(batch));
    }

    // 2. Update lifecycle stage [cite: 159]
    @PutMapping("/{id}/status")
    public ResponseEntity<CropBatch> updateStatus(@PathVariable Long id, @RequestParam CropStatus status) {
        CropBatch batch = cropRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Batch not found"));
        batch.setStatus(status);
        return ResponseEntity.ok(cropRepo.save(batch));
    }

    // 3. View current inventory [cite: 161]
    @GetMapping
    public ResponseEntity<List<CropBatch>> getAllCrops() {
        return ResponseEntity.ok(cropRepo.findAll());
    }
}
