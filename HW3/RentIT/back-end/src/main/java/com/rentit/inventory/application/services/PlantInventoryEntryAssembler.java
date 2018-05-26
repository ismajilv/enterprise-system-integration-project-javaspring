package com.rentit.inventory.application.services;

import com.rentit.inventory.application.dto.PlantInventoryEntryDTO;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.rest.InventoryRestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class PlantInventoryEntryAssembler {

    public PlantInventoryEntryDTO toResource(PlantInventoryEntry plant) {
        PlantInventoryEntryDTO dto = new PlantInventoryEntryDTO();
        dto.set_id(plant.getId());
        dto.setName(plant.getName());
        dto.setDescription(plant.getDescription());
        dto.setPrice(plant.getPrice());
        return dto;
    }
}