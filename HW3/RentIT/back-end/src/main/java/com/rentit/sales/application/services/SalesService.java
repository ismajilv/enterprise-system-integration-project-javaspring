package com.rentit.sales.application.services;

import com.rentit.common.application.exceptions.PlantNotFoundException;
import com.rentit.common.domain.model.BusinessPeriod;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.model.PlantInventoryItem;
import com.rentit.inventory.domain.model.PlantReservation;
import com.rentit.inventory.domain.repository.InventoryRepository;
import com.rentit.inventory.domain.repository.PlantInventoryEntryRepository;
import com.rentit.inventory.domain.repository.PlantInventoryItemRepository;
import com.rentit.inventory.domain.repository.PlantReservationRepository;
import com.rentit.sales.domain.model.PurchaseOrder;
import com.rentit.sales.domain.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SalesService {

    @Autowired
    PlantInventoryEntryRepository plantInventoryEntryRepository;
    @Autowired
    PlantInventoryItemRepository plantInventoryItemRepository;
    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    PlantReservationRepository plantReservationRepository;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrder findPurchaseOrder(Long id) {
        return purchaseOrderRepository.getOne(id);
    }

    public List<PurchaseOrder>findAllPurchaseOrder() {
        return purchaseOrderRepository.findAll();
    }

    public PurchaseOrder createPurchaseOrder(Long plantId, LocalDate startDate, LocalDate endDate) throws PlantNotFoundException {
        PlantInventoryEntry plant = plantInventoryEntryRepository.getOne(plantId);
        PurchaseOrder po = PurchaseOrder.of(
                plant,
                BusinessPeriod.of(startDate, endDate));

        // Validate PO
        purchaseOrderRepository.save(po);

        List<PlantInventoryItem> items = inventoryRepository.findAvailableItems(plant, startDate, endDate);

        if (!items.isEmpty()) {
            PlantReservation reservation = new PlantReservation();
            reservation.setPlant(items.get(0));
            reservation.setSchedule(BusinessPeriod.of(startDate, endDate));
            plantReservationRepository.save(reservation);

            po.registerFirstAllocation(reservation);
            // Validate PO
            purchaseOrderRepository.save(po);

        } else {
            po.reject();
            purchaseOrderRepository.save(po);
        }

        return po;
    }
}
