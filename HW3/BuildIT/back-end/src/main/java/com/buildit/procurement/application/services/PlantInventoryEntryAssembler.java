package com.buildit.procurement.application.services;

import com.buildit.procurement.application.dto.PlantInventoryEntryDTO;
import com.buildit.procurement.web.controller.PlantHireRequestController;
import com.buildit.procurement.domain.model.PlantInventoryEntry;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class PlantInventoryEntryAssembler extends ResourceAssemblerSupport<PlantInventoryEntry, PlantInventoryEntryDTO> {

    public PlantInventoryEntryAssembler() {
        super(PlantHireRequestController.class, PlantInventoryEntryDTO.class);
    }

    @Override
    public PlantInventoryEntryDTO toResource(PlantInventoryEntry plantInventoryEntry) {
        PlantInventoryEntryDTO dto = createResourceWithId(plantInventoryEntry.getId(), plantInventoryEntry);

        dto.setHref(plantInventoryEntry.getId());
        dto.setName(plantInventoryEntry.getName());

        return dto;
    }

}
