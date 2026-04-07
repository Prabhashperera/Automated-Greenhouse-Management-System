package com.project.cropinventoryservice.repo;

import com.project.cropinventoryservice.entity.CropBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CropRepo extends JpaRepository<CropBatch, Long> {
}
