package com.project.zonemanagementservice.repo;

import com.project.zonemanagementservice.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepo extends JpaRepository<Zone, Long> {
}
