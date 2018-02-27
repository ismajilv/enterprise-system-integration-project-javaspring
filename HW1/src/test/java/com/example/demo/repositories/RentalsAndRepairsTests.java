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

    private PlantInventoryItem createPIItemFor(PlantInventoryEntry entry, EquipmentCondition condition) {
        PlantInventoryItem item = new PlantInventoryItem();
        item.setEquipmentCondition(condition);
        item.setPlantInfo(entry);
        return plantInventoryItemRepository.save(item);
    }

    private void createReservationFor(PlantInventoryItem plantInventoryItem, LocalDate from, LocalDate to) {
        PlantReservation reservation = new PlantReservation();
        reservation.setPlant(plantInventoryItem);
        reservation.setSchedule(BusinessPeriod.of(from, to)); // CHANGED
        plantReservationRepository.save(reservation);
    }

    private void createMaintenanceTaskForYear(int year, TypeOfWork typeOfWork, BigDecimal price, PlantInventoryItem item) {
        MaintenancePlan plan = new MaintenancePlan();
        plan.setYearOfAction(year);
        plan.setPlant(item);
        MaintenanceTask task = new MaintenanceTask();
        plan.getTasks().add(task);
        task.setTypeOfWork(typeOfWork);
        task.setPrice(price);
        maintenancePlanRepository.save(plan);
    }

    @Test
    public void findRentalsAndRepairs() {
        List<PlantsWithRentalsAndRepairs> rentalsAndRepairs = plantInventoryEntryRepository.findRentalsAndRepairs(2017);

        List<PlantInventoryEntry> allPlants = plantInventoryEntryRepository.findAll();

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
                .forEach(rentals -> assertEquals(rentals, new Integer(0)));

        rentalsAndRepairs.stream().map(tuple -> tuple.getRepairs())
                .forEach(repairs -> assertEquals(repairs, new Integer(0)));


    }
}


