package com.rentit.test;

import com.rentit.inventory.domain.model.EquipmentCondition;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.model.PlantInventoryItem;
import com.rentit.inventory.domain.repository.PlantInventoryEntryRepository;
import com.rentit.inventory.domain.repository.PlantInventoryItemRepository;
import com.rentit.sales.domain.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TestHelperController {
    PlantInventoryEntryRepository plantInventoryEntryRepository;
    PlantInventoryItemRepository plantInventoryItemRepository;
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    void setup(PlantInventoryEntryRepository entryRepository, PlantInventoryItemRepository itemRepository, PurchaseOrderRepository purchaseOrderRepository) {
        this.plantInventoryEntryRepository = entryRepository;
        this.plantInventoryItemRepository = itemRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @PostMapping("/api/entries")
    public List<PlantInventoryEntry> setupInventory(@RequestBody List<PlantInventoryEntry> entries) {
        purchaseOrderRepository.deleteAll();
        plantInventoryItemRepository.deleteAll();
        plantInventoryEntryRepository.deleteAll();
//        return plantInventoryEntryRepository.saveAll(entries);
        entries = plantInventoryEntryRepository.saveAll(entries);

        List<PlantInventoryItem> items = entries.stream()
                .map(entry -> PlantInventoryItem.of(null, "sn-" + entry.getId(), EquipmentCondition.SERVICEABLE, entry))
                .collect(Collectors.toList());

        plantInventoryItemRepository.saveAll(items);

        return entries;
    }

    @PostMapping("/api/items")
    public List<PlantInventoryItem> setupItems(@RequestBody List<PlantInventoryItem> items) {
        return plantInventoryItemRepository.saveAll(items);
    }

    @GetMapping("/api/po/count")
    public Integer countPurchaseOrders(){
        return purchaseOrderRepository.findAll().size();
    }
}