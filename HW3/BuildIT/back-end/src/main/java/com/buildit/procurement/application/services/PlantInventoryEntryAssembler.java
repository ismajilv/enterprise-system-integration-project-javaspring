package com.buildit.procurement.application.services;

import com.buildit.procurement.application.dto.PlantHireRequestDTO;
import com.buildit.procurement.application.dto.PlantInventoryEntryDTO;
import com.buildit.procurement.controller.rest.PlantHireRestController;
import com.buildit.procurement.domain.model.PlantHireRequest;
import com.buildit.procurement.domain.model.PlantInventoryEntry;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class PlantInventoryEntryAssembler extends ResourceAssemblerSupport<PlantInventoryEntry, PlantInventoryEntryDTO> {

    public PlantInventoryEntryAssembler() {
        super(PlantHireRestController.class, PlantInventoryEntryDTO.class);

    }

    @Override
    public PlantInventoryEntryDTO toResource(PlantInventoryEntry plantInventoryEntry) {
        PlantInventoryEntryDTO dto = createResourceWithId(plantInventoryEntry.getHref(), plantInventoryEntry);

        dto.setHref(plantInventoryEntry.getHref());

        return dto;
    }

}
