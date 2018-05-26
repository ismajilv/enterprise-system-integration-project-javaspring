package com.rentit.test;

import com.rentit.common.domain.model.BusinessPeriod;
import com.rentit.inventory.domain.model.EquipmentCondition;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.model.PlantInventoryItem;
import com.rentit.inventory.domain.repository.PlantInventoryEntryRepository;
import com.rentit.inventory.domain.repository.PlantInventoryItemRepository;
import com.rentit.sales.application.dto.PurchaseOrderDTO;
import com.rentit.sales.application.exceptions.POStatusException;
import com.rentit.sales.application.services.PurchaseOrderAssembler;
import com.rentit.sales.application.services.SalesService;
import com.rentit.sales.domain.model.PurchaseOrder;
import com.rentit.sales.domain.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TestHelperController {
    PlantInventoryEntryRepository plantInventoryEntryRepository;
    PlantInventoryItemRepository plantInventoryItemRepository;
    PurchaseOrderRepository purchaseOrderRepository;
    SalesService salesService;
    PurchaseOrderAssembler purchaseOrderAssembler;

    @Autowired
    void setup(PlantInventoryEntryRepository entryRepository,
               PlantInventoryItemRepository itemRepository,
               PurchaseOrderRepository purchaseOrderRepository,
               SalesService salesService,
    PurchaseOrderAssembler purchaseOrderAssembler) {
        this.plantInventoryEntryRepository = entryRepository;
        this.plantInventoryItemRepository = itemRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.salesService = salesService;
        this.purchaseOrderAssembler = purchaseOrderAssembler;
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

    @PostMapping("/api/pos")
    public List<PurchaseOrder> setupPOs() {
        List<PurchaseOrder> pos = purchaseOrderRepository.findAll();
        List<PlantInventoryItem> pii = plantInventoryItemRepository.findAll();

        for(int i= 0; i < pos.size(); i++){
            if(i==0){
                try {
                    final PurchaseOrder cancelPurchaseOrder = salesService.cancelPurchaseOrder(pos.get(i).getId());
                    PurchaseOrderDTO poDTO = purchaseOrderAssembler.toResource(cancelPurchaseOrder);
                    salesService.notifyCustomer(poDTO);
                } catch (POStatusException e) {
                    e.printStackTrace();
                }
            } else if (i>1){
                PurchaseOrder acceptedPO = salesService.acceptPurchaseOrder(pos.get(i).getId(), pii.get(i).getId());
                PurchaseOrderDTO poDTO = purchaseOrderAssembler.toResource(acceptedPO);
                salesService.notifyCustomer(poDTO);
                if(i==3){
                    PurchaseOrder rejectedByCustomer = salesService.customerRejectPurchaseOrder(pos.get(i).getId());
                    PurchaseOrderDTO rejDTO = purchaseOrderAssembler.toResource(rejectedByCustomer);
                    salesService.notifyCustomer(rejDTO);
                }
                if(i>3){
                    PurchaseOrder deliverPurchaseOrder = salesService.deliverPurchaseOrder(pos.get(i).getId());
                    PurchaseOrderDTO deliveredDTO = purchaseOrderAssembler.toResource(deliverPurchaseOrder);
                    salesService.notifyCustomer(deliveredDTO);
                    if(i>4){
                        PurchaseOrder returnedPO = salesService.markAsReturned(pos.get(i).getId());
                        PurchaseOrderDTO returnedDTO = purchaseOrderAssembler.toResource(returnedPO);
                        salesService.notifyCustomer(returnedDTO);
                    }
                }
            }
        }
        return purchaseOrderRepository.findAll();
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