package com.rentit.inventory.domain.repository;

import com.rentit.RentitApplication;
import com.rentit.common.domain.model.BusinessPeriod;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.model.PlantInventoryItem;
import com.rentit.inventory.domain.model.PlantReservation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RentitApplication.class)
@Sql(scripts = "/plants-dataset.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class InventoryRepositoryTest {
    @Autowired
    PlantInventoryEntryRepository plantInventoryEntryRepo;

    @Autowired
    PlantInventoryItemRepository plantInventoryItemRepo;

    @Autowired
    PlantReservationRepository plantReservationRepo;

    @Autowired
    InventoryRepository inventoryRepo;

    @Test
    public void queryPlantCatalog() {
        assertThat(plantInventoryEntryRepo.count()).isEqualTo(14);
    }

    @Test
    public void queryByName() {
        assertThat(plantInventoryEntryRepo.findByNameContaining("Mini").size()).isEqualTo(2);
        assertThat(plantInventoryEntryRepo.finderMethod("Mini").size()).isEqualTo(2);
        assertThat(plantInventoryEntryRepo.finderMethodV2("mini").size()).isEqualTo(2);
    }

    @Test
    public void findAvailableTest() {
        PlantInventoryEntry entry = plantInventoryEntryRepo.findOneById(14);
        PlantInventoryItem item = plantInventoryItemRepo.findOneByPlantInfo(entry);

        assertThat(inventoryRepo.findAvailablePlants("Mini excavator",LocalDate.of(2017,2,25), LocalDate.of(2017,05,15)))
                .contains(entry);

        PlantReservation po = new PlantReservation();
        po.setPlant(item);
        po.setSchedule(BusinessPeriod.of(LocalDate.of(2017, 2, 20), LocalDate.of(2017, 2, 25)));
        plantReservationRepo.save(po);

        assertThat(inventoryRepo.findAvailablePlants("Mini excavator", LocalDate.of(2017,2,20), LocalDate.of(2017,2,25)))
                .doesNotContain(entry);
    }
}
