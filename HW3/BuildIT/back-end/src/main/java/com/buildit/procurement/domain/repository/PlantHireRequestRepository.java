package com.buildit.procurement.domain.repository;

import com.buildit.procurement.domain.model.PlantHireRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantHireRequestRepository extends JpaRepository<PlantHireRequest, Long> {
}
