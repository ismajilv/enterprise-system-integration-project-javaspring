package com.example.demo.inventory.application.services;

import com.example.demo.inventory.application.dto.PlantInventoryEntryDTO;
import com.example.demo.inventory.domain.model.PlantInventoryEntry;
import com.example.demo.inventory.domain.repository.PlantInventoryEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public class InventoryService {
    @Autowired
    PlantInventoryEntryRepository inventoryRepository;

    @Autowired
    PlantInventoryEntryAssembler plantInventoryEntryAssembler;

    public List<PlantInventoryEntryDTO> findAvailable(String plantName, LocalDate startDate, LocalDate endDate) {
        List<PlantInventoryEntry> res = inventoryRepository.findAvailablePlants(plantName,startDate, endDate);
        return plantInventoryEntryAssembler.toResources(res);
    }
}
