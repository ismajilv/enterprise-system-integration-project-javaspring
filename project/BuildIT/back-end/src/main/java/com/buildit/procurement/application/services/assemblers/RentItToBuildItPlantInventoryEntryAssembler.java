package com.buildit.procurement.application.services.assemblers;

import com.buildit.procurement.application.dto.PlantInventoryEntryDTO;
import com.buildit.procurement.application.dto.RentItPlantInventoryEntryDTO;
import com.buildit.procurement.web.controller.PlantHireRequestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class RentItToBuildItPlantInventoryEntryAssembler extends ResourceAssemblerSupport<RentItPlantInventoryEntryDTO, PlantInventoryEntryDTO> {

	public RentItToBuildItPlantInventoryEntryAssembler() {
		super(PlantHireRequestController.class, PlantInventoryEntryDTO.class);
	}

	@Override
	public PlantInventoryEntryDTO toResource(RentItPlantInventoryEntryDTO plant) {
		PlantInventoryEntryDTO dto = createResourceWithId(plant.get_id(), plant);

		dto.setHref(plant.get_links().get("self").get("href"));
		dto.setName(plant.getName());
		dto.setPricePerDay(plant.getPrice());
		dto.setExternalId(plant.get_id());

		dto.removeLinks();

		return dto;
	}

}
