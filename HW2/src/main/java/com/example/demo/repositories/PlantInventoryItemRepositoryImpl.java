package com.example.demo.repositories;

import com.example.demo.models.PlantInventoryItem;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

public class PlantInventoryItemRepositoryImpl implements PlantsNotHiredRepository {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<PlantInventoryItem> findPlantsNotHiredForPeriod(LocalDate start, LocalDate end) {
		return null;
	}

}
