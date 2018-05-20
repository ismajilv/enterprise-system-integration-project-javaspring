package com.rentit.test;

import com.rentit.inventory.domain.model.EquipmentCondition;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.model.PlantInventoryItem;
import com.rentit.inventory.domain.repository.InventoryRepository;
import com.rentit.inventory.domain.repository.PlantInventoryEntryRepository;
import com.rentit.inventory.domain.repository.PlantInventoryItemRepository;
import com.rentit.inventory.domain.repository.PlantReservationRepository;
import com.rentit.sales.domain.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

// TODO Delete before going to production!
@Service
public class TestDataProvider {

	@Autowired
	PlantInventoryEntryRepository plantInventoryEntryRepository;
	@Autowired
	PlantInventoryItemRepository plantInventoryItemRepository;
	@Autowired
	InventoryRepository inventoryRepository;
	@Autowired
	PlantReservationRepository plantReservationRepository;
	@Autowired
	PurchaseOrderRepository purchaseOrderRepository;

	@PostConstruct
	public void addTestData() {
		for (Long i = 1l; i <= 5l; i++) {
			PlantInventoryEntry entry = new PlantInventoryEntry();

			// entry.setId(i);
			entry.setName("excavator type " + i);
			entry.setPrice(new BigDecimal(i * 100));
			entry.setDescription("description " + i);

			plantInventoryEntryRepository.saveAndFlush(entry);

			PlantInventoryItem item = PlantInventoryItem.of(i, "A" + i, EquipmentCondition.SERVICEABLE, entry);

			plantInventoryItemRepository.save(item);
		}
	}

}
