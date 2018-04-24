package com.rentit.inventory.application.services;

import com.rentit.inventory.application.dto.PlantInventoryEntryDTO;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    public List<PlantInventoryEntry> findAvailable(String plantName, LocalDate startDate, LocalDate endDate) {
        return inventoryRepository.findAvailablePlants(plantName,startDate, endDate);
    }
}
