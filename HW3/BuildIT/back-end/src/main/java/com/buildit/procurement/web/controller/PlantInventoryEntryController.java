package com.buildit.procurement.web.controller;

import com.buildit.procurement.application.dto.PlantHireRequestDTO;
import com.buildit.procurement.application.dto.PlantInventoryEntryDTO;
import com.buildit.procurement.application.services.PlantInventoryEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/plants")
public class PlantInventoryEntryController {

	@Autowired
	PlantInventoryEntryService service;

	@GetMapping
	public Collection<PlantInventoryEntryDTO> readAll() {
		Collection<PlantInventoryEntryDTO> plants = service.getAll();

		plants.forEach(p -> p.removeLinks());

		return plants;
	}

}
