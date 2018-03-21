package com.example.demo.inventory.domain.repository;

import com.example.demo.inventory.domain.model.PlantsWithCount;
import com.example.demo.inventory.domain.model.PlantsWithRentalsAndRepairs;
import com.example.demo.inventory.domain.model.PlantInventoryEntry;

import java.time.LocalDate;
import java.util.List;

public interface PlantInventoryEntryRepositoryCustomQueries {

	boolean isThereAnyAvailableItem(PlantInventoryEntry entry, LocalDate start, LocalDate end);

	List<PlantsWithCount> findAvailable(String name, LocalDate start, LocalDate end);

	List<PlantsWithRentalsAndRepairs> findRentalsAndRepairs(int year);

	List<PlantInventoryEntry> findAvailablePlants(String name, LocalDate startDate, LocalDate endDate);
}

