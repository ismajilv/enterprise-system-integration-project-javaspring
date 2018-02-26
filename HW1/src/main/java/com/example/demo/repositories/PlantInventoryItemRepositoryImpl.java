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
		return entityManager.createQuery("select pii from PlantInventoryItem pii where " +
				"pii.id not in (select pr.plant.id from PlantReservation pr where " +
				"pr.schedule.startDate <= ?2 and pr.schedule.endDate >= ?1 group by pr.plant)", PlantInventoryItem.class)
				.setParameter(1, start).setParameter(2, end).getResultList();
	}

}
