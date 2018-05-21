package com.buildit.procurement.application.services;

import com.buildit.procurement.application.dto.ExtensionDTO;
import com.buildit.procurement.domain.enums.ERStatus;
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
	CommentAssembler assembler;

	@Autowired
	PlantHireRequestService plantHireRequestService;

	@Transactional
	public ExtensionDTO create(Long plantHireRequestId, LocalDate newEndDate) {
		PlantHireRequest plantHireRequest = plantHireRequestService.readModel(plantHireRequestId);

		ExtensionRequest request = new ExtensionRequest();

		request.setPlantHireRequest(plantHireRequest);

		request.setNewEndDate(newEndDate);

		request.setState(ERStatus.PENDING);

		request = repository.save(request);

		return null; // TODO assembler.toResource(request);
	}

}