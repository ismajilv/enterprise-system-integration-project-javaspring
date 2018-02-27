package com.example.demo.repositories;

import com.example.demo.models.PlantInventoryEntry;
import com.example.demo.models.PlantsWithCount;
import com.example.demo.models.PlantsWithRentalsAndRepairs;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

import static com.example.demo.models.EquipmentCondition.SERVICEABLE;

@Repository
@Transactional(readOnly = true)
public class PlantInventoryEntryRepositoryImpl implements PlantInventoryEntryRepositoryCustomQueries {

	@PersistenceContext
	EntityManager entityManager;


	@Override
	public boolean isThereAnyAvailableItem(PlantInventoryEntry entry, LocalDate start, LocalDate end) {
		return entityManager.createQuery("select case when (count(pii) > 0) then true else false end from " +
				"PlantInventoryItem  pii where pii.plantInfo = ?1 and pii.equipmentCondition = ?2 and " +
				"pii.id not in (select pr.plant.id from PlantReservation pr where " +
				"pr.schedule.startDate <= ?4 and pr.schedule.endDate >= ?3)", Boolean.class)
				.setParameter(1, entry).setParameter(2, SERVICEABLE).setParameter(3, start)
				.setParameter(4, end).getSingleResult();
	}

	@Override
	public List<PlantsWithCount> findAvailable(String name, LocalDate start, LocalDate end) {
		return entityManager.createQuery("select new " +
						"com.example.demo.models.PlantsWithCount(pii.plantInfo, count(pii)) " +
						"from PlantInventoryItem pii where pii.equipmentCondition = ?4 and " +
						"lower(pii.plantInfo.name) like lower(concat('%', ?1,'%')) " +
						"and pii.id not in (select pr.plant.id from PlantReservation pr where " +
						"pr.schedule.startDate <= ?3 and pr.schedule.endDate >= ?2) group by pii.plantInfo",
				PlantsWithCount.class).setParameter(1, name).setParameter(2, start).setParameter(3, end)
				.setParameter(4, SERVICEABLE).getResultList();
	}

	@Override
	public List<PlantsWithRentalsAndRepairs> findRentalsAndRepairs(int year) {
		return entityManager.createQuery(
				"select new com.example.demo.models.PlantsWithRentalsAndRepairs(pie, count(purchaseOrders), count(tasks)) " +
						"from PlantInventoryEntry pie " +
						"left join pie.purchaseOrders purchaseOrders on purchaseOrders.status = com.example.demo.models.POStatus.CLOSED and YEAR(purchaseOrders.issueDate) = ?1 " +
						"left join pie.items items " +
						"left join items.maintenancePlans maintenancePlans on maintenancePlans.yearOfAction = ?1 " +
						"left join maintenancePlans.tasks tasks on tasks.typeOfWork = com.example.demo.models.TypeOfWork.CORRECTIVE " +
						"group by pie " +
						"order by count(purchaseOrders) DESC, count(tasks) ASC", PlantsWithRentalsAndRepairs.class)
				.setParameter(1, year)
				.getResultList();
	}

}
