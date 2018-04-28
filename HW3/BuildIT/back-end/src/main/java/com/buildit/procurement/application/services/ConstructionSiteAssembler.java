package com.buildit.procurement.application.services;

import com.buildit.procurement.application.dto.ConstructionSiteDTO;
import com.buildit.procurement.domain.model.ConstructionSite;
import com.buildit.procurement.web.controller.PlantHireRequestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class ConstructionSiteAssembler extends ResourceAssemblerSupport<ConstructionSite, ConstructionSiteDTO> {

	public ConstructionSiteAssembler() {
		super(PlantHireRequestController.class, ConstructionSiteDTO.class);
	}

	@Override
	public ConstructionSiteDTO toResource(ConstructionSite constructionSite) {
		ConstructionSiteDTO dto = createResourceWithId(constructionSite.getId(), constructionSite);

		dto.set_id(constructionSite.getId());
		dto.setAddress(constructionSite.getAddress());

		return dto;
	}

}
