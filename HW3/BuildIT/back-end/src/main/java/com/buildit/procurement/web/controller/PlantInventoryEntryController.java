package com.buildit.procurement.web.controller;

import com.buildit.procurement.application.dto.PlantInventoryEntryDTO;
import com.buildit.procurement.application.services.PlantInventoryEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/plants")
public class PlantInventoryEntryController {

	@Autowired
	PlantInventoryEntryService service;

	@GetMapping
	public Collection<PlantInventoryEntryDTO> getAll() {
		return service.getAll();
	}

}
