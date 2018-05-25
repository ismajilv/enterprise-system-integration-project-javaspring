package com.buildit.procurement.application.services.assemblers;

import com.buildit.procurement.application.dto.ExtensionRequestDTO;
import com.buildit.procurement.domain.model.ExtensionRequest;
import com.buildit.procurement.web.controller.PlantHireRequestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class ExtensionRequestAssembler extends ResourceAssemblerSupport<ExtensionRequest, ExtensionRequestDTO> {

	public ExtensionRequestAssembler() {
		super(PlantHireRequestController.class, ExtensionRequestDTO.class);
	}

	@Override
	public ExtensionRequestDTO toResource(ExtensionRequest request) {
		ExtensionRequestDTO dto = createResourceWithId(request.getId(), request);

		dto.set_id(request.getId());
		dto.setNewEndDate(request.getNewEndDate());
		dto.setComment(request.getComment());

		return dto;
	}

}
