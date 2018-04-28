package com.buildit.procurement.application.services;

import com.buildit.common.application.dto.MoneyDTO;
import com.buildit.procurement.application.dto.PlantInventoryEntryDTO;
import com.buildit.procurement.domain.model.PlantInventoryEntry;
import com.buildit.procurement.domain.repository.PlantInventoryEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class PlantInventoryEntryService {

	@Autowired
	PlantInventoryEntryRepository repository;
	@Autowired
	PlantInventoryEntryAssembler assembler;

	// mock querying from RentIt for now
	private Map<String, PlantInventoryEntryDTO> href2Plant = new HashMap<>();

	{
		PlantInventoryEntryDTO plant1 = PlantInventoryEntryDTO.of(
				"http://ramirent.ee:9550/api/plants/2",
				"BOBCAT E19CAB",
				MoneyDTO.of(BigDecimal.valueOf(159.99))
		);

		PlantInventoryEntryDTO plant2 = PlantInventoryEntryDTO.of(
				"http://ramirent.ee:9550/api/plants/59",
				"KAESER M122",
				MoneyDTO.of(BigDecimal.valueOf(1800))
		);

		PlantInventoryEntryDTO plant3 = PlantInventoryEntryDTO.of(
				"http://www.cramo.ee/api/item-json/55",
				"WACKER-NEUSON DPU6555HE",
				MoneyDTO.of(BigDecimal.valueOf(620.50))
		);

		href2Plant.put(plant1.getHref(), plant1);

		href2Plant.put(plant2.getHref(), plant2);

		href2Plant.put(plant3.getHref(), plant3);
	}

	// TODO should be queried from different RentIts and assembled together
	public Collection<PlantInventoryEntryDTO> getAll() {
		return href2Plant.values();
	}

	// TODO should be queried
	public PlantInventoryEntryDTO fetchByHref(String href) {
		PlantInventoryEntryDTO maybePlantInventoryEntry = href2Plant.get(href);

		if (isNull(maybePlantInventoryEntry)) {
			throw new IllegalArgumentException("Cannot fetch plant inventory entry by href: " + href);
		}

		return maybePlantInventoryEntry;
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
