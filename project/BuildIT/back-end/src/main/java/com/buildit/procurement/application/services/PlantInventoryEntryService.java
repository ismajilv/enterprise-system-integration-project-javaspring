package com.buildit.procurement.application.services;

import com.buildit.procurement.application.dto.PlantInventoryEntryDTO;
import com.buildit.procurement.application.services.assemblers.PlantInventoryEntryAssembler;
import com.buildit.procurement.application.services.assemblers.RentItToBuildItPlantInventoryEntryAssembler;
import com.buildit.procurement.domain.model.PlantInventoryEntry;
import com.buildit.procurement.domain.model.Supplier;
import com.buildit.procurement.domain.repository.PlantInventoryEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@Service
public class PlantInventoryEntryService {

	@Autowired
	PlantInventoryEntryRepository repository;

	@Autowired
    PlantInventoryEntryAssembler assembler;

	@Autowired
    RentItToBuildItPlantInventoryEntryAssembler rentItToBuildItPlantInventoryEntryAssembler;

	@Autowired
	RentItService integrationService;

	@Autowired
	SupplierService supplierService;

	public PlantInventoryEntryDTO fetchByHref(String href) {
		return rentItToBuildItPlantInventoryEntryAssembler.toResource(integrationService.fetchPlantEntryFromRentIt(href));
	}

	@Transactional
	public PlantInventoryEntry readOrCreateModel(String href) {
		Optional<PlantInventoryEntry> maybePlantInventoryEntry = repository.findById(href);

		PlantInventoryEntry plant;

		if (!maybePlantInventoryEntry.isPresent()) {
			PlantInventoryEntryDTO fetched = fetchByHref(href);

			Supplier supplier = supplierService.getFirstAsModel(); // TODO needs to be changed to one that is queried

			plant = PlantInventoryEntry.of(href, null, fetched.getName(), supplier); // TODO needs external ID

			plant = repository.save(plant);
		} else {
			plant = maybePlantInventoryEntry.get();
		}

		return plant;
	}

	public Collection<PlantInventoryEntryDTO> findAvailable(String plantName, LocalDate startDate, LocalDate endDate) {
		return integrationService.queryPlantCatalog(plantName, startDate, endDate);
	}

}
