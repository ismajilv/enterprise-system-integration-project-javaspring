package com.buildit.procurement.application.services.assemblers;

import com.buildit.procurement.application.dto.PlantInventoryEntryDTO;
import com.buildit.procurement.web.controller.PlantHireRequestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;
import com.buildit.procurement.application.dto.Team1PlantInventoryEntryDTO;

@Service
public class Team1ToBuildItPlantInventoryEntryAssembler extends ResourceAssemblerSupport<Team1PlantInventoryEntryDTO, PlantInventoryEntryDTO> {

    public Team1ToBuildItPlantInventoryEntryAssembler() {
        super(PlantHireRequestController.class, PlantInventoryEntryDTO.class);
    }

    @Override
    public PlantInventoryEntryDTO toResource(Team1PlantInventoryEntryDTO plant) {
        PlantInventoryEntryDTO dto = createResourceWithId(plant.get_id(), plant);

        dto.setHref(plant.get_links().get("self").get("href"));
        dto.setName(plant.getName());
        dto.setPricePerDay(plant.getPrice());
        dto.setExternalId(plant.get_id());

        dto.removeLinks();

        return dto;
    }
}
