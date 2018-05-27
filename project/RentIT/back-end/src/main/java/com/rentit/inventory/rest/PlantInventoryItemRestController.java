package com.rentit.inventory.rest;

import com.rentit.inventory.application.dto.PlantInventoryItemDTO;
import com.rentit.inventory.application.services.PlantInventoryEntryAssembler;
import com.rentit.inventory.application.services.PlantInventoryItemAssembler;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.model.PlantInventoryItem;
import com.rentit.inventory.domain.repository.InventoryRepository;
import com.rentit.inventory.domain.repository.PlantInventoryEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/pitems")
public class PlantInventoryItemRestController {

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    PlantInventoryEntryRepository plantInventoryEntryRepository;

    @Autowired
    PlantInventoryItemAssembler plantInventoryItemAssembler;

    @GetMapping("/items")
    public List<PlantInventoryItemDTO> findAvailableInventoryItems(
            @RequestParam(name = "pieId") Long pieId,
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        PlantInventoryEntry plant = plantInventoryEntryRepository.getOne(pieId);
        List<PlantInventoryItem> items = inventoryRepository.findAvailableItems(plant, startDate, endDate);
        return items.stream().map(pii -> plantInventoryItemAssembler.toResource(pii)).collect(Collectors.toList());
    }

}
