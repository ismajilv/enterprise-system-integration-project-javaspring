package com.buildit.procurement.application.services;

import com.buildit.procurement.application.dto.ExtensionRequestDTO;
import com.buildit.procurement.application.services.assemblers.ExtensionRequestAssembler;
import com.buildit.procurement.domain.model.ExtensionRequest;
import com.buildit.procurement.domain.model.PlantHireRequest;
import com.buildit.procurement.domain.repository.ExtensionRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ExtensionRequestService {

	@Autowired
	ExtensionRequestRepository repository;

	@Autowired
    ExtensionRequestAssembler assembler;

	@Autowired
	PlantHireRequestService plantHireRequestService;

	@Transactional
	public ExtensionRequestDTO create(Long plantHireRequestId, LocalDate newEndDate) {
		PlantHireRequest plantHireRequest = plantHireRequestService.readModel(plantHireRequestId);

		ExtensionRequest request = new ExtensionRequest();

		request.setPlantHireRequest(plantHireRequest);

		request.setNewEndDate(newEndDate);

		request = repository.save(request);

		return assembler.toResource(request);
	}

}