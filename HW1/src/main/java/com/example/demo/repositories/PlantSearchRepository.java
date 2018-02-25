package com.example.demo.repositories;

import com.example.demo.models.PlantInventoryEntry;
import com.example.demo.models.PlantsWithCount;

import java.time.LocalDate;
import java.util.List;

public interface PlantSearchRepository {

	boolean isThereAnyAvailableItem(PlantInventoryEntry entry, LocalDate start, LocalDate end);

	List<PlantsWithCount> findAvailable(String name, LocalDate start, LocalDate end);

}
