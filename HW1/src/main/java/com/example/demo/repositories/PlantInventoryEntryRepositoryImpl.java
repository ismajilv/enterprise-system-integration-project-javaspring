package com.example.demo.repositories;

import com.example.demo.models.PlantInventoryEntry;
import com.example.demo.models.PlantsWithCount;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class PlantInventoryEntryRepositoryImpl implements PlantSearchRepository {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public boolean isThereAnyAvailableItem(PlantInventoryEntry entry, LocalDate start, LocalDate end) {
		return false;
	}

	@Override
	public List<PlantsWithCount> findAvailable(String name, LocalDate start, LocalDate end) {
		return null;
	}

}
