package com.buildit.procurement.application.services;

import com.buildit.procurement.application.dto.PlantInventoryEntryDTO;
import com.buildit.procurement.domain.model.PlantInventoryEntry;
import com.buildit.procurement.web.controller.PlantHireRequestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class PlantInventoryEntryAssembler extends ResourceAssemblerSupport<PlantInventoryEntry, PlantInventoryEntryDTO> {

	@Autowired
	SupplierAssembler supplierAssembler;

	public PlantInventoryEntryAssembler() {
		super(PlantHireRequestController.class, PlantInventoryEntryDTO.class);
	}

	@Override
	public PlantInventoryEntryDTO toResource(PlantInventoryEntry plantInventoryEntry) {
		PlantInventoryEntryDTO dto = createResourceWithId(plantInventoryEntry.getHref(), plantInventoryEntry);

		dto.setHref(plantInventoryEntry.getHref());
		dto.setExternalId(plantInventoryEntry.getExternalId());
		dto.setName(plantInventoryEntry.getName());
		dto.setPricePerDay(null); // price does not get stored, as it will be stale data
		dto.setSupplier(supplierAssembler.toResource(plantInventoryEntry.getSupplier()));

		return dto;
	}

}
