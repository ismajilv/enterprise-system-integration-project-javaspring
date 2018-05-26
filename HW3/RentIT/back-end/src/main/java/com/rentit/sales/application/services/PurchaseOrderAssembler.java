package com.rentit.sales.application.services;

import com.rentit.common.application.dto.BusinessPeriodDTO;
import com.rentit.inventory.application.services.PlantInventoryEntryAssembler;
import com.rentit.sales.application.dto.PurchaseOrderDTO;
import com.rentit.sales.domain.model.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderAssembler {

    @Autowired
    PlantInventoryEntryAssembler plantInventoryEntryAssembler;

    public PurchaseOrderDTO toResource(PurchaseOrder po) {
        PurchaseOrderDTO dto = new PurchaseOrderDTO();
        dto.set_id(po.getId());
        dto.setPlant(plantInventoryEntryAssembler.toResource(po.getPlant()));
        dto.setRentalPeriod(BusinessPeriodDTO.of(po.getRentalPeriod().getStartDate(), po.getRentalPeriod().getEndDate()));
        dto.setTotal(po.getTotal());
        dto.setStatus(po.getStatus());

        return dto;
    }
}