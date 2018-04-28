package com.buildit.procurement.application.services;

import com.buildit.procurement.application.dto.PlantInventoryEntryDTO;
import com.buildit.procurement.domain.model.PlantInventoryEntry;
import com.buildit.procurement.domain.repository.PlantInventoryEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class PlantInventoryEntryService {

	@Autowired
	PlantInventoryEntryRepository repository;

	@Autowired
	PlantInventoryEntryAssembler assembler;

	@Autowired
	ExternalIntegrationsService integrationService;

	public Collection<PlantInventoryEntryDTO> getAll() {
		return integrationService.readAllExternal();
	}

	public PlantInventoryEntryDTO fetchByHref(String href) {
		return integrationService.readOneExternal(href);
	}

	@Transactional
	public PlantInventoryEntry readOrCreateModel(String href) {
		Optional<PlantInventoryEntry> maybePlantInventoryEntry = repository.findById(href);

		PlantInventoryEntry plant;

		if (!maybePlantInventoryEntry.isPresent()) {
			PlantInventoryEntryDTO fetched = fetchByHref(href);

			plant = PlantInventoryEntry.of(href, fetched.getName());

			repository.save(plant);
		} else {
			plant = maybePlantInventoryEntry.get();
		}

		return plant;
	}

}
