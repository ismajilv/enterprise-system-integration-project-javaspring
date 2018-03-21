package com.example.demo.inventory.domain.repository;

import com.example.demo.inventory.domain.model.MaintenancePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenancePlanRepository extends JpaRepository<MaintenancePlan, Long>, MaintenancePlanRepositoryCustomQueries {
}
