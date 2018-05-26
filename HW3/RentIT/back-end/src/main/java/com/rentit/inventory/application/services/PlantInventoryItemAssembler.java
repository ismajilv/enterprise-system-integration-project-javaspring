package com.rentit.inventory.application.services;

import com.rentit.inventory.application.dto.PlantInventoryItemDTO;
import com.rentit.inventory.domain.model.PlantInventoryItem;
import org.springframework.stereotype.Service;

@Service
public class PlantInventoryItemAssembler {

    public PlantInventoryItemDTO toResource(PlantInventoryItem plant) {
        PlantInventoryItemDTO dto = new PlantInventoryItemDTO();
        dto.set_id(plant.getId());
        return dto;
    }
}