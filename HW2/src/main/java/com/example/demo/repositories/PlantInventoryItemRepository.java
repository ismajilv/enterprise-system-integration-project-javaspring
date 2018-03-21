package com.example.demo.repositories;

import com.example.demo.models.PlantInventoryEntry;
import com.example.demo.models.PlantInventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantInventoryItemRepository extends JpaRepository<PlantInventoryItem, Long>, PlantsNotHiredRepository {

	List<PlantInventoryItem> findByPlantInfo(PlantInventoryEntry entry);

}