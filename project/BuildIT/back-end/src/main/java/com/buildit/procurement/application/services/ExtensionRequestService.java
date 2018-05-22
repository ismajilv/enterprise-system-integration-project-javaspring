package com.buildit.procurement.application.services;

import com.buildit.procurement.application.dto.ExtensionRequestDTO;
import com.buildit.procurement.application.services.assemblers.ExtensionRequestAssembler;
import com.buildit.procurement.domain.model.ExtensionRequest;
import com.buildit.procurement.domain.model.PlantHireRequest;
import com.buildit.procurement.domain.repository.ExtensionRequestRepository;
import com.buildit.procurement.domain.repository.PlantHireRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ExtensionRequestService {

	@Autowired
	ExtensionRequestRepository repository;

	@Autowired
<<<<<<< HEAD
    ExtensionRequestAssembler assembler;
=======
	PlantHireRequestRepository plantHireRequestRepository;

	@Autowired
	ExtensionRequestAssembler assembler;
>>>>>>> db27d439948c39c9c06729e4dca5e33e7c9bb672

	@Autowired
	PlantHireRequestService plantHireRequestService;

	@Transactional
	public ExtensionRequestDTO create(Long plantHireRequestId, LocalDate newEndDate) {
		PlantHireRequest plantHireRequest = plantHireRequestService.readModel(plantHireRequestId);

		ExtensionRequest extensionRequest = new ExtensionRequest();

		extensionRequest.setPlantHireRequest(plantHireRequest);

		extensionRequest.setNewEndDate(newEndDate);

		// TODO needs to go to RentIt with this...

		extensionRequest = repository.save(extensionRequest);

		plantHireRequest.setExtensionRequest(extensionRequest);

		plantHireRequest = plantHireRequestRepository.save(plantHireRequest);

		return assembler.toResource(extensionRequest);
	}

}