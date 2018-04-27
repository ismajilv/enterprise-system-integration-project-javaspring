package com.buildit.procurement.application.services;

import com.buildit.procurement.domain.model.ConstructionSite;
import com.buildit.procurement.domain.model.PlantInventoryEntry;
import com.buildit.procurement.domain.repository.PlantInventoryEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlantInventoryEntryService {

	@Autowired
	private PlantInventoryEntryRepository plantInventoryEntryRepository;

	public PlantInventoryEntry getByHref(String href) {
		Optional<PlantInventoryEntry> maybePlantInventoryEntry = plantInventoryEntryRepository.findById(href);

		if (!maybePlantInventoryEntry.isPresent()) {
			throw new IllegalArgumentException("Cannot find plant inventory entry by href: " + href);
		}

		return maybePlantInventoryEntry.get();
	}

	public PlantInventoryEntry create(String href, String name) {
		PlantInventoryEntry plant = PlantInventoryEntry.of(href, name);

		plantInventoryEntryRepository.save(plant);

		return plant;
	}

}
