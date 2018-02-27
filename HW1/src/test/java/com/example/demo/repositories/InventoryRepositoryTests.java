package com.example.demo.repositories;

import com.example.demo.DemoApplication;
import com.example.demo.models.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@Sql(scripts="/test-dataset.sql")
@DirtiesContext(classMode=DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class InventoryRepositoryTests {

	@Autowired
	PlantInventoryEntryRepository plantInventoryEntryRepository;
	@Autowired
	PlantInventoryItemRepository plantInventoryItemRepository;
	@Autowired
	PlantReservationRepository plantReservationRepository;
	@Autowired
	MaintenancePlanRepository maintenancePlanRepository;
	@Autowired
	MaintenanceTaskRepository maintenanceTaskRepository;

	private PlantInventoryItem createPIItemFor(PlantInventoryEntry entry, EquipmentCondition condition) {
		PlantInventoryItem item = new PlantInventoryItem();
		item.setEquipmentCondition(condition);
		item.setPlantInfo(entry);
		return plantInventoryItemRepository.save(item);
	}

	private PlantReservation createReservationFor(PlantInventoryItem plantInventoryItem, LocalDate from, LocalDate to) {
		PlantReservation reservation = new PlantReservation();
		reservation.setPlant(plantInventoryItem);
		reservation.setSchedule(BusinessPeriod.of(from, to)); // CHANGED
		return plantReservationRepository.save(reservation);
	}

	private MaintenanceTask createMaintenanceTaskFor(MaintenancePlan maintenancePlan, PlantReservation plantReservation){
		MaintenanceTask task = new MaintenanceTask();
		task.setPrice(BigDecimal.TEN);
		task.setTypeOfWork(TypeOfWork.OPERATIVE);
		task.setDescription("description");
		task.setReservation(plantReservation);
		task.setMaintenancePlan(maintenancePlan);
		maintenancePlan.getTasks().add(task);
		return maintenanceTaskRepository.save(task);
	}

	private MaintenancePlan createMaintenancePlanFor(PlantInventoryItem plantInventoryItem, int year) {
		MaintenancePlan maintenancePlan = new MaintenancePlan();
		maintenancePlan.setPlant(plantInventoryItem);
		maintenancePlan.setYearOfAction(year);
		return maintenancePlanRepository.save(maintenancePlan);
	}

	@Test
	public void findRentalsAndRepairs() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate from = LocalDate.parse("16/08/2018", formatter);

		List<PlantInventoryEntry> allPlants = plantInventoryEntryRepository.findAll();

		// 1 repair 2 reservation
		PlantInventoryEntry plant1 = allPlants.get(0);
		PlantInventoryItem inv1 = createPIItemFor(plant1, EquipmentCondition.SERVICEABLE);

		PlantReservation reservation1_1 = createReservationFor(inv1, from, from.plusDays(1));
		PlantReservation reservation1_2 = createReservationFor(inv1, from.minusDays(3), from.minusDays(2));
		MaintenancePlan maintenancePlan1 = createMaintenancePlanFor(inv1, from.getYear());
		MaintenanceTask task1 = createMaintenanceTaskFor(maintenancePlan1, reservation1_1);

		// 2 repair 3 reservations
		PlantInventoryEntry plant2 = allPlants.get(1);
		PlantInventoryItem inv2 = createPIItemFor(plant2, EquipmentCondition.SERVICEABLE);
		PlantReservation reservation2_1 = createReservationFor(inv2, from, from.plusDays(1));
		PlantReservation reservation2_2 = createReservationFor(inv2, from.minusDays(3), from.minusDays(2));
		PlantReservation reservation2_3 = createReservationFor(inv2, from.plusDays(2), from.plusDays(3));

		//extra reservations with different years
		PlantReservation reservationNotIn2018_1 = createReservationFor(inv2, from.plusDays(4).minusYears(1), from.plusDays(5).minusYears(1));
		PlantReservation reservationNotIn2018_2 = createReservationFor(inv2, from.plusDays(6).minusYears(1), from.plusDays(7).minusYears(1));
		PlantReservation reservationNotIn2018_3 = createReservationFor(inv2, from.plusDays(4).plusYears(1), from.plusDays(5).plusYears(1));
		PlantReservation reservationNotIn2018_4 = createReservationFor(inv2, from.plusDays(6).plusYears(1), from.plusDays(7).plusYears(1));

		MaintenancePlan maintenancePlan2Not2018_1 = createMaintenancePlanFor(inv2, from.getYear() - 1);
		MaintenancePlan maintenancePlan2Not2018_2 = createMaintenancePlanFor(inv2, from.getYear() + 1);
		MaintenanceTask task2Not2018_1 = createMaintenanceTaskFor(maintenancePlan2Not2018_1, reservationNotIn2018_1);
		MaintenanceTask task2Not2018_2 = createMaintenanceTaskFor(maintenancePlan2Not2018_2, reservationNotIn2018_3);

		MaintenancePlan maintenancePlan2 = createMaintenancePlanFor(inv2, from.getYear());
		MaintenanceTask task2 = createMaintenanceTaskFor(maintenancePlan2, reservation2_1);
		MaintenanceTask task2_2 = createMaintenanceTaskFor(maintenancePlan2, reservation2_2);

		// 2 repair 3 reservations
		PlantInventoryEntry plant3 = allPlants.get(2);
		PlantInventoryItem inv3 = createPIItemFor(plant3, EquipmentCondition.SERVICEABLE);
		PlantReservation reservation3_1 = createReservationFor(inv3, from, from.plusDays(1));
		PlantReservation reservation3_2 = createReservationFor(inv3, from.minusDays(3), from.minusDays(2));
		PlantReservation reservation3_3 = createReservationFor(inv3, from.plusDays(2), from.plusDays(3));

		MaintenancePlan maintenancePlan3 = createMaintenancePlanFor(inv3, from.getYear());
		MaintenanceTask task3 = createMaintenanceTaskFor(maintenancePlan3, reservation3_1);
		MaintenanceTask task3_2 = createMaintenanceTaskFor(maintenancePlan3, reservation3_2);

		// 2 repair 4 reservations
		PlantInventoryEntry plant4 = allPlants.get(3);
		PlantInventoryItem inv4 = createPIItemFor(plant4, EquipmentCondition.SERVICEABLE);
		PlantReservation reservation4_1 = createReservationFor(inv4, from, from.plusDays(1));
		PlantReservation reservation4_2 = createReservationFor(inv4, from.minusDays(3), from.minusDays(2));
		PlantReservation reservation4_3 = createReservationFor(inv4, from.plusDays(2), from.plusDays(3));
		PlantReservation reservation4_4 = createReservationFor(inv4, from.plusDays(4), from.plusDays(5));

		MaintenancePlan maintenancePlan4 = createMaintenancePlanFor(inv4, from.getYear());
		MaintenanceTask task4 = createMaintenanceTaskFor(maintenancePlan4, reservation4_1);
		MaintenanceTask task4_2 = createMaintenanceTaskFor(maintenancePlan4, reservation4_2);

		// 3 repair 3 reservations
		PlantInventoryEntry plant5 = allPlants.get(4);
		PlantInventoryItem inv5 = createPIItemFor(plant5, EquipmentCondition.SERVICEABLE);
		PlantReservation reservation5_1 = createReservationFor(inv5, from, from.plusDays(1));
		PlantReservation reservation5_2 = createReservationFor(inv5, from.minusDays(3), from.minusDays(2));
		PlantReservation reservation5_3 = createReservationFor(inv5, from.plusDays(2), from.plusDays(3));

		MaintenancePlan maintenancePlan5 = createMaintenancePlanFor(inv5, from.getYear());
		MaintenanceTask task5 = createMaintenanceTaskFor(maintenancePlan5, reservation5_1);
		MaintenanceTask task5_2 = createMaintenanceTaskFor(maintenancePlan5, reservation5_2);
		MaintenanceTask task5_3 = createMaintenanceTaskFor(maintenancePlan5, reservation5_3);
		
		List<PlantsWithRentalsAndRepairs> rentalsAndRepairs = plantInventoryEntryRepository.findRentalsAndRepairs(from.getYear());

		assertThat(rentalsAndRepairs.stream().map(tuple -> tuple.getEntry()).collect(Collectors.toList()))
				.containsAll(allPlants)
				.hasSize(15);

		assertThat(maintenancePlanRepository.findAll()).hasSize(7);
		assertThat(maintenanceTaskRepository.findAll()).hasSize(12);

		for(PlantsWithRentalsAndRepairs plantsWithRentalsAndRepairs: rentalsAndRepairs) {
			if(plantsWithRentalsAndRepairs.getEntry().getId().equals(plant1.getId())){
				assertEquals(new Long(1), new Long(plantsWithRentalsAndRepairs.getRentals()));
				assertEquals(new Long(2), new Long(plantsWithRentalsAndRepairs.getRepairs()));
			} else if(plantsWithRentalsAndRepairs.getEntry().getId().equals(plant2.getId())) {
				assertEquals(new Long(2), new Long(plantsWithRentalsAndRepairs.getRentals()));
				assertEquals(new Long(3), new Long(plantsWithRentalsAndRepairs.getRepairs()));
			} else if(plantsWithRentalsAndRepairs.getEntry().getId().equals(plant3.getId())) {
				assertEquals(new Long(2), new Long(plantsWithRentalsAndRepairs.getRentals()));
				assertEquals(new Long(3), new Long(plantsWithRentalsAndRepairs.getRepairs()));
			} else if(plantsWithRentalsAndRepairs.getEntry().getId().equals(plant4.getId())) {
				assertEquals(new Long(2), new Long(plantsWithRentalsAndRepairs.getRentals()));
				assertEquals(new Long(4), new Long(plantsWithRentalsAndRepairs.getRepairs()));
			} else {
				assertEquals(new Long(3), new Long(plantsWithRentalsAndRepairs.getRentals()));
				assertEquals(new Long(3), new Long(plantsWithRentalsAndRepairs.getRepairs()));
			}

		}

		//make a copy of returned list
		List<PlantsWithRentalsAndRepairs> copyOfList = new ArrayList<>();
		for(PlantsWithRentalsAndRepairs repairs: rentalsAndRepairs){
			copyOfList.add(repairs);
		}
		//sort the copy list (original list not affected by the sort)
		Collections.sort(copyOfList, (p1, p2) ->
				p1.getRentals().compareTo(p2.getRentals()) != 0 ? p1.getRentals().compareTo(p2.getRentals()) : p1.getRepairs().compareTo(p2.getRepairs()));

		//if copy equals original, then original was in correct order from start
		//two lists are defined to be equal if they contain the same elements in the same order.
		assertEquals(copyOfList, rentalsAndRepairs);

		List<PlantsWithRentalsAndRepairs> rentalsAndRepairs2 = plantInventoryEntryRepository.findRentalsAndRepairs(2017);

		rentalsAndRepairs2.stream().map(tuple -> tuple.getRentals())
				.forEach(rentals -> assertEquals(new Long(0), rentals));

		rentalsAndRepairs2.stream().map(tuple -> tuple.getRepairs())
				.forEach(repairs -> assertEquals(new Long(0), repairs));
	}

// TODO saves time, but uncomment once rentals & repairs is ready
//
//	@Test
//	public void plantsAvailableForABusinessPeriod() {
//		LocalDate from = LocalDate.now();
//		LocalDate to = from.plusDays(2);
//
//		// The original dataset has 6 different plant inventory entries
//		List<PlantInventoryEntry> excavators = plantInventoryEntryRepository.findByNameContaining("excavator");
//		assertThat(excavators).hasSize(6);
//
//		// Let us just keep the three randomly chosen excavators (note that the list is shuffled)
//		Collections.shuffle(excavators);
//		excavators = excavators.subList(0, 3);
//		// and then create plant inventory items for them
//		// (2 for the first one in the shuffled list and 1 for the two remaining)
//		for (PlantInventoryEntry excavator: excavators) createPIItemFor(excavator, EquipmentCondition.SERVICEABLE);
//		createPIItemFor(excavators.get(0), EquipmentCondition.SERVICEABLE);
//		createPIItemFor(excavators.get(1), EquipmentCondition.UNSERVICEABLE_REPAIRABLE);
//
//		// Let us check that we have exactly three different types excavators with a least one
//		// available physical equipment for each one of them
//		List<PlantsWithCount> availableExcavators = plantInventoryEntryRepository.findAvailable("excavator", from, to);
//
//		assertThat(availableExcavators.stream().map(tuple -> tuple.getEntry()).collect(Collectors.toList()))
//				.containsAll(excavators)
//				.hasSize(3);
//
//		// Now, let us assume that we reserve three of the available plants
//		for (PlantInventoryEntry entry: excavators) {
//			List<PlantInventoryItem> items = plantInventoryItemRepository.findByPlantInfo(entry);
//			createReservationFor(items.get(0), from, to);
//		}
//
//		// We should have only one available plant left
//		availableExcavators = plantInventoryEntryRepository.findAvailable("excavator", from, to);
//		assertThat(availableExcavators.stream().map(tuple -> tuple.getEntry()).collect(Collectors.toList()))
//				.containsAll(Collections.singletonList(excavators.get(0)))
//				.hasSize(1);
//
//		// Sanity check!
//		assertThat(plantInventoryEntryRepository.findAvailable("excavator", from.plusWeeks(1), to.plusWeeks(1)))
//				.hasSize(3);
//	}
//
//	@Test
//	public void checkingIfPlantIsAvailable() {
//		LocalDate from = LocalDate.now();
//		LocalDate to = from.plusDays(2);
//		Random random = new Random();
//
//		// The original dataset has 6 different plant inventory entries
//		List<PlantInventoryEntry> excavators = plantInventoryEntryRepository.findByNameContaining("excavator");
//
//		// We select one excavator (entry) at random and add one plant (item) for it
//		PlantInventoryEntry excavator = excavators.get(random.nextInt(excavators.size()));
//		PlantInventoryItem item = createPIItemFor(excavator, EquipmentCondition.SERVICEABLE);
//		createPIItemFor(excavator, EquipmentCondition.UNSERVICEABLE_REPAIRABLE);
//
//		// We query availability that excavator (it must return true)
//		assertThat(plantInventoryEntryRepository.isThereAnyAvailableItem(excavator, from, to))
//				.isTrue();
//
//		// We reserve the plant (item) for two days starting today
//		createReservationFor(item, from, to);
//
//		// The plant should not be available for the same period as above
//		assertThat(plantInventoryEntryRepository.isThereAnyAvailableItem(excavator, from, to))
//				.isFalse();
//		// But it should be available for the week after
//		assertThat(plantInventoryEntryRepository.isThereAnyAvailableItem(excavator, from.plusWeeks(1), to.plusWeeks(1)))
//				.isTrue();
//	}
//
//	@Test
//	public void findPlantsNotHiredForAGivenPeriodOfTime() {
//		Random random = new Random();
//		LocalDate beginningOfPeriod = LocalDate.now().minusMonths(6);
//
//		// We create one plant item per entry
//		for (PlantInventoryEntry entry: plantInventoryEntryRepository.findByNameContaining("excavator"))
//			createPIItemFor(entry, EquipmentCondition.SERVICEABLE);
//
//		// We shuffle the list of plants to enforce a random order on its elements
//		List<PlantInventoryItem> items = plantInventoryItemRepository.findAll();
//		Collections.shuffle(items);
//
//		// We partition the list of plants into those that will be kept as idle for a fixed period
//		List<PlantInventoryItem> idleItems = items.subList(0, 2);
//		// and a sublist of them that will be simulated as hired for the same period
//		List<PlantInventoryItem> hiredItems = items.subList(2, 6);
//
//		for (PlantInventoryItem item: hiredItems) {
//			LocalDate from = LocalDate.now().minusWeeks(random.nextInt(12) + 12);
//			// We simulate a couple of reservations that happened inside the period
//			createReservationFor(item, from, from.plusDays(2));
//			createReservationFor(item, from.plusWeeks(12), from.plusWeeks(12).plusDays(2));
//		}
//
//		for (PlantInventoryItem item: items) {
//			// For the entire list of plants, we will simulate a reservation outside the period
//			LocalDate from = LocalDate.now().minusWeeks(random.nextInt(12) + 40);
//			createReservationFor(item, from, from.plusDays(2));
//		}
//
//		assertThat(plantInventoryItemRepository.findPlantsNotHiredForPeriod(beginningOfPeriod, LocalDate.now()))
//				.containsAll(idleItems)
//				.doesNotContainAnyElementsOf(hiredItems);
//	}

}