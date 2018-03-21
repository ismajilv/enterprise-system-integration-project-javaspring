package com.example.demo.repositories;

import com.example.demo.DemoApplication;
import com.example.demo.common.domain.model.BusinessPeriod;
import com.example.demo.inventory.domain.model.*;
import com.example.demo.inventory.domain.repository.PlantInventoryEntryRepository;
import com.example.demo.inventory.domain.repository.PlantInventoryItemRepository;
import com.example.demo.inventory.domain.repository.PlantReservationRepository;
import com.example.demo.inventory.domain.repository.PurchaseOrderRepository;
import com.example.demo.inventory.domain.model.PlantInventoryEntry;
import com.example.demo.inventory.domain.model.PlantInventoryItem;
import com.example.demo.sales.domain.model.PurchaseOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@SpringBootTest(classes = DemoApplication.class)
@Sql(scripts= "/dataset/test-dataset.sql")
@DirtiesContext(classMode=DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RentalsAndRepairsTests {

    @Autowired
    PlantInventoryItemRepository plantInventoryItemRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private PlantReservationRepository plantReservationRepository;

    @Autowired
    PlantInventoryEntryRepository plantInventoryEntryRepository;

    private PlantInventoryItem createPIItemFor(PlantInventoryEntry entry, EquipmentCondition condition) {
        PlantInventoryItem item = new PlantInventoryItem();
        item.setEquipmentCondition(condition);
        item.setPlantInfo(entry);
        return plantInventoryItemRepository.save(item);
    }

    private PurchaseOrder createPurchaseorder(PlantInventoryItem item, LocalDate from, LocalDate to) {
    PurchaseOrder order = new PurchaseOrder();
    order.setPlant(item.getPlantInfo());
    PlantReservation reservation = createReservationFor(item, from, to);
    order.getReservations().add(reservation);
    order.setRentalPeriod(BusinessPeriod.of(from, to));
    return purchaseOrderRepository.save(order);
    }



    private PlantReservation createReservationFor (PlantInventoryItem plantInventoryItem, LocalDate from, LocalDate to) {
        PlantReservation reservation = new PlantReservation();
        reservation.setPlant(plantInventoryItem);
        reservation.setSchedule(BusinessPeriod.of(from, to)); // CHANGED
        return plantReservationRepository.save(reservation);
    }


    @Test
    public void findrentalsbyyear() {
        int thisYear = LocalDate.now().getYear();
        List<PlantInventoryEntry> excavators = plantInventoryEntryRepository.findByNameContaining("excavator");

        List<PlantsWithRentalsAndRepairs> expectedResults = new ArrayList<>();
        for (PlantInventoryEntry excavator: excavators) {
            PlantInventoryItem item = createPIItemFor(excavator, EquipmentCondition.SERVICEABLE);
            for (int year = thisYear-3;year<=thisYear;year++){
                createPurchaseorder(item, LocalDate.of(year,6,1), LocalDate.of(year,6,5));
                expectedResults.add(PlantsWithRentalsAndRepairs.of(excavator,year,1L,0L));
            }
        }

        System.out.println(expectedResults);
    }
}


