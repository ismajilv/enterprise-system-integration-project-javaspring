package com.rentit.inventory.rest;

import com.rentit.common.application.exceptions.PlantNotFoundException;
import com.rentit.inventory.application.dto.PlantInventoryEntryDTO;
import com.rentit.inventory.application.services.InventoryService;
import com.rentit.inventory.application.services.PlantInventoryEntryAssembler;
import com.rentit.inventory.domain.model.EquipmentCondition;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.model.PlantInventoryItem;
import com.rentit.inventory.domain.repository.InventoryRepository;
import com.rentit.inventory.domain.repository.PlantInventoryEntryRepository;
import com.rentit.inventory.domain.repository.PlantInventoryItemRepository;
import com.rentit.inventory.domain.repository.PlantReservationRepository;
import com.rentit.sales.application.dto.PurchaseOrderDTO;
import com.rentit.sales.application.services.PurchaseOrderAssembler;
import com.rentit.sales.application.services.SalesService;
import com.rentit.sales.domain.model.PurchaseOrder;
import com.rentit.sales.domain.repository.PurchaseOrderRepository;
import com.rentit.sales.rest.SalesRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("api/test")
// TODO delete this before going to production!
public class TestDataGenerationRestController {

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

	@GetMapping("/addData")
	public ResponseEntity addTestData() {

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

		return new ResponseEntity<>("Test data added successfully", HttpStatus.CREATED);
	}

}
