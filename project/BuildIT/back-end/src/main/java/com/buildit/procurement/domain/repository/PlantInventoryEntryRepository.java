package com.buildit.procurement.domain.repository;

import com.buildit.procurement.domain.model.PlantInventoryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantInventoryEntryRepository extends JpaRepository<PlantInventoryEntry, String> {
}