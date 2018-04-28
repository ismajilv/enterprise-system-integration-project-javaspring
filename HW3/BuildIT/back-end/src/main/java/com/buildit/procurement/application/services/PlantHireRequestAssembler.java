package com.buildit.procurement.application.services;

import com.buildit.common.application.service.BusinessPeriodAssembler;
import com.buildit.common.application.service.MoneyAssembler;
import com.buildit.procurement.application.dto.CommentDTO;
import com.buildit.procurement.application.dto.PlantHireRequestDTO;
import com.buildit.procurement.domain.model.PlantHireRequest;
import com.buildit.procurement.web.controller.PlantHireRequestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class PlantHireRequestAssembler extends ResourceAssemblerSupport<PlantHireRequest, PlantHireRequestDTO> {

	@Autowired
	CommentAssembler commentAssembler;

	@Autowired
	ConstructionSiteAssembler constructionSiteAssembler;

	@Autowired
	BusinessPeriodAssembler businessPeriodAssembler;

	@Autowired
	SupplierAssembler supplierAssembler;

	@Autowired
	PlantInventoryEntryAssembler plantInventoryEntryAssembler;

	@Autowired
	MoneyAssembler moneyAssembler;

	@Autowired
	PurchaseOrderAssembler purchaseOrderAssembler;

	public PlantHireRequestAssembler() {
		super(PlantHireRequestController.class, PlantHireRequestDTO.class);

	}

	@Override
	public PlantHireRequestDTO toResource(PlantHireRequest plantHireRequest) {
		PlantHireRequestDTO dto = createResourceWithId(plantHireRequest.getId(), plantHireRequest);

		dto.set_id(plantHireRequest.getId());

		List<CommentDTO> commentDTOs = commentAssembler.toResources(plantHireRequest.getComments());
		dto.setComments(commentDTOs);

		dto.setSite(constructionSiteAssembler.toResource(plantHireRequest.getConstructionSite()));

		dto.setRentalPeriod(businessPeriodAssembler.toResource(plantHireRequest.getRentalPeriod()));

		dto.setSupplier(supplierAssembler.toResource(plantHireRequest.getSupplier()));

		dto.setPlant(plantInventoryEntryAssembler.toResource(plantHireRequest.getPlant()));

		dto.setRentalCost(moneyAssembler.toResource(plantHireRequest.getRentalCost()));

		dto.setStatus(plantHireRequest.getStatus());

		if (!isNull(plantHireRequest.getPurchaseOrder())) {
			dto.setPurchaseOrder(purchaseOrderAssembler.toResource(plantHireRequest.getPurchaseOrder()));
		}

		return dto;
	}

}
