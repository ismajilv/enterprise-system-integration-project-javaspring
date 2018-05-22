package com.buildit.procurement.application.services.assemblers;

import com.buildit.common.application.service.BusinessPeriodAssembler;
import com.buildit.common.application.service.EmployeeAssembler;
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
	PurchaseOrderAssembler purchaseOrderAssembler;

	@Autowired
	EmployeeAssembler employeeAssembler;

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

		dto.setRentalCost(plantHireRequest.getRentalCost());

		dto.setStatus(plantHireRequest.getStatus());

		dto.setRequestingSiteEngineer(employeeAssembler.toResource(plantHireRequest.getRequestingSiteEngineer()));

		if (!isNull(plantHireRequest.getPurchaseOrder())) {
			dto.setPurchaseOrder(purchaseOrderAssembler.toResource(plantHireRequest.getPurchaseOrder()));
		}

		if (!isNull(plantHireRequest.getApprovingWorksEngineer())) {
			dto.setApprovingWorksEngineer(employeeAssembler.toResource(plantHireRequest.getApprovingWorksEngineer()));
		}

		return dto;
	}

}
