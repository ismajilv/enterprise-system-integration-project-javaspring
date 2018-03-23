package com.rentit.inventory.rest;

import com.rentit.inventory.application.dto.PlantInventoryItemDTO;
import com.rentit.inventory.application.dto.PlantReservationDTO;
import com.rentit.inventory.application.services.PlantInventoryItemAssembler;
import com.rentit.inventory.application.services.PlantReservationAssembler;
import com.rentit.inventory.domain.model.PlantReservation;
import com.rentit.inventory.domain.repository.InventoryRepository;
import com.rentit.inventory.domain.repository.PlantInventoryEntryRepository;
import com.rentit.inventory.domain.repository.PlantReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
public class PlantReservationRestController {

	@Autowired
	PlantReservationRepository plantReservationRepository;

	@Autowired
	PlantReservationAssembler plantReservationAssembler;

	@GetMapping
	public List<PlantReservationDTO> getAll() {
		List<PlantReservation> all = plantReservationRepository.findAll();

		return all.stream().map(pii -> plantReservationAssembler.toResource(pii)).collect(Collectors.toList());
	}

}