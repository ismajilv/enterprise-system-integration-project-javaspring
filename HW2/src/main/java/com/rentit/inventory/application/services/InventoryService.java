package com.rentit.inventory.application.services;

import com.rentit.inventory.application.dto.PlantInventoryEntryDTO;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.model.PlantInventoryItem;
import com.rentit.inventory.domain.repository.InventoryRepository;
import com.rentit.inventory.domain.repository.PlantInventoryEntryRepository;
import com.rentit.inventory.domain.repository.PlantInventoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    PlantInventoryItemRepository plantInventoryItemRepository;

    @Autowired
    PlantInventoryEntryAssembler plantInventoryEntryAssembler;

    @Autowired
    PlantInventoryEntryRepository plantInventoryEntryRepository;

    public List<PlantInventoryEntryDTO> findAvailable(String plantName, LocalDate startDate, LocalDate endDate) {
        List<PlantInventoryEntry> res = inventoryRepository.findAvailablePlants(plantName,startDate, endDate);
        return plantInventoryEntryAssembler.toResources(res);
    }

    public PlantInventoryItem findPlantInventoryItem(Long id) {
        return plantInventoryItemRepository.getOne(id);
    }

    public PlantInventoryEntry findPlantInventoryEntry(Long id) { return  plantInventoryEntryRepository.getOne(id);}
}
