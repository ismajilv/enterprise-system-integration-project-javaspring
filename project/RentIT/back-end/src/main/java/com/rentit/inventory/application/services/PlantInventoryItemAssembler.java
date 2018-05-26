package com.rentit.inventory.application.services;

import com.rentit.inventory.application.dto.PlantInventoryItemDTO;
import com.rentit.inventory.domain.model.PlantInventoryItem;
import com.rentit.inventory.rest.PlantInventoryItemRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class PlantInventoryItemAssembler extends ResourceAssemblerSupport<PlantInventoryItem, PlantInventoryItemDTO> {

    @Autowired
    PlantInventoryEntryAssembler plantInventoryEntryAssembler;

    public PlantInventoryItemAssembler() {
        super(PlantInventoryItemRestController.class, PlantInventoryItemDTO.class);
    }

    public PlantInventoryItemDTO toResource(PlantInventoryItem plant) {
        PlantInventoryItemDTO dto = createResourceWithId(plant.getId(), plant);
        dto.set_id(plant.getId());
        dto.setSerialNumber(plant.getSerialNumber());
        dto.setPlantInfo(plantInventoryEntryAssembler.toResource(plant.getPlantInfo()));
        return dto;
    }
}