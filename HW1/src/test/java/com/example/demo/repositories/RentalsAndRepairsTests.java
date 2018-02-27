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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@Sql(scripts="/rental-dataset.sql")
@DirtiesContext(classMode=DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RentalsAndRepairsTests {
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

        LocalDate from = LocalDate.now();
        LocalDate to = from.plusDays(2);

        List<PlantInventoryEntry> allPlants = plantInventoryEntryRepository.findAll();

        Collections.shuffle(allPlants);
        allPlants = allPlants.subList(0, 5);

        for (PlantInventoryEntry plant: allPlants){
            PlantInventoryItem plantInventoryItem = createPIItemFor(plant, EquipmentCondition.SERVICEABLE);
            PlantReservation reservation = createReservationFor(plantInventoryItem, from, to);
            MaintenancePlan maintenancePlan = createMaintenancePlanFor(plantInventoryItem, from.getYear());
            MaintenanceTask task = createMaintenanceTaskFor(maintenancePlan, reservation);
        }

        List<PlantsWithRentalsAndRepairs> rentalsAndRepairs = plantInventoryEntryRepository.findRentalsAndRepairs(2017);



        assertThat(rentalsAndRepairs.stream().map(tuple -> tuple.getEntry()).collect(Collectors.toList()))
                .containsAll(allPlants)
                .hasSize(15);

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

        rentalsAndRepairs.stream().map(tuple -> tuple.getRentals())
                .forEach(rentals -> assertEquals(new Long(0), rentals));

        rentalsAndRepairs.stream().map(tuple -> tuple.getRepairs())
                .forEach(repairs -> assertEquals(new Long(0), repairs));


    }
}


