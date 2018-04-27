package com.buildit.procurement.application.services;

import com.buildit.procurement.application.dto.PlantInventoryEntryDTO;
import com.buildit.procurement.domain.model.PlantInventoryEntry;
import com.buildit.procurement.domain.repository.PlantInventoryEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class PlantInventoryEntryService {

	@Autowired
	PlantInventoryEntryRepository repository;

	@Autowired
	PlantInventoryEntryAssembler assembler;

	public PlantInventoryEntry getByHref(String href) {
		Optional<PlantInventoryEntry> maybePlantInventoryEntry = repository.findById(href);

		if (!maybePlantInventoryEntry.isPresent()) {
			throw new IllegalArgumentException("Cannot find plant inventory entry by href: " + href);
		}

		return maybePlantInventoryEntry.get();
	}

	public PlantInventoryEntry create(String href, String name) {
		PlantInventoryEntry plant = PlantInventoryEntry.of(href, name);

		repository.save(plant);

		return plant;
	}

	// TODO should be queried from different RentIts and assembled together
	public Collection<PlantInventoryEntryDTO> getAll() {
		return assembler.toResources(repository.findAll());
	}

}
