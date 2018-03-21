package com.rentit.inventory.domain.repository;

import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.model.PlantInventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantInventoryItemRepository extends JpaRepository<PlantInventoryItem, Long> {
    PlantInventoryItem findOneByPlantInfo(PlantInventoryEntry entry);
}
