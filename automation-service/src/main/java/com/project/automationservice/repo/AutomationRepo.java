package com.project.automationservice.repo;

import com.project.automationservice.entity.AutomationAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutomationRepo extends JpaRepository<AutomationAction, Long> {
}
