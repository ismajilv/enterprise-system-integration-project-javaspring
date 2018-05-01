package com.buildit.procurement.web.controller;

import com.buildit.procurement.application.dto.PlantInventoryEntryDTO;
import com.buildit.procurement.application.services.PlantInventoryEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;

@RestController
@CrossOrigin(origins = "http://localhost:6080")
@RequestMapping("/api/plants")
public class PlantInventoryEntryController {

	@Autowired
	PlantInventoryEntryService service;

	@GetMapping
	public Collection<PlantInventoryEntryDTO> findAvailable(
			@RequestParam(name = "name") String plantName,
			@RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

		Collection<PlantInventoryEntryDTO> plants = service.findAvailable(plantName, startDate, endDate);

		plants.forEach(p -> p.removeLinks());

		return plants;
	}

}
