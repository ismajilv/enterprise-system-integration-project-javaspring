package com.example.demo.repositories;

import com.example.demo.models.PlantInventoryEntry;
import com.example.demo.models.PlantsWithCount;
import com.example.demo.models.PlantsWithRentalsAndRepairs;

import java.time.LocalDate;
import java.util.List;

public interface PlantInventoryEntryRepositoryCustomQueries {

	boolean isThereAnyAvailableItem(PlantInventoryEntry entry, LocalDate start, LocalDate end);

	List<PlantsWithCount> findAvailable(String name, LocalDate start, LocalDate end);

	List<PlantsWithRentalsAndRepairs> findRentalsAndRepairs();

}
