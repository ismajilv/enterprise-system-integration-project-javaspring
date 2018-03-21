package com.rentit.sales.application.services;

import com.rentit.common.application.exceptions.PlantNotFoundException;
import com.rentit.common.domain.model.BusinessPeriod;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.repository.PlantInventoryEntryRepository;
import com.rentit.sales.application.dto.PurchaseOrderDTO;
import com.rentit.sales.domain.model.PurchaseOrder;
import com.rentit.sales.domain.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalesService {

    @Autowired
    PlantInventoryEntryRepository plantInventoryEntryRepository;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    PurchaseOrderAssembler purchaseOrderAssembler;

   public PurchaseOrderDTO findPurchaseOrder(Long id) {
       PurchaseOrder po = purchaseOrderRepository.findOne(id);
       return purchaseOrderAssembler.toResource(po);
   }

    public PurchaseOrderDTO createPurchaseOrder(PurchaseOrderDTO partialPODTO) throws PlantNotFoundException {
        PlantInventoryEntry plant = plantInventoryEntryRepository.findOne(partialPODTO.getPlant().get_id());
        BusinessPeriod rentalPeriod = BusinessPeriod.of(partialPODTO.getRentalPeriod().getStartDate(), partialPODTO.getRentalPeriod().getEndDate());

        PurchaseOrder po = PurchaseOrder.of(
                plant,
                rentalPeriod);

        po = purchaseOrderRepository.save(po);

        return purchaseOrderAssembler.toResource(po);
    }

}
