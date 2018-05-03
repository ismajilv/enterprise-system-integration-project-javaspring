package com.rentit.sales.application.services;

import com.rentit.common.application.dto.BusinessPeriodDTO;
import com.rentit.inventory.application.services.PlantInventoryEntryAssembler;
import com.rentit.sales.application.dto.PurchaseOrderDTO;
import com.rentit.sales.domain.model.PurchaseOrder;
import com.rentit.sales.rest.SalesRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class PurchaseOrderAssembler extends ResourceAssemblerSupport<PurchaseOrder, PurchaseOrderDTO> {

    @Autowired
    PlantInventoryEntryAssembler plantInventoryEntryAssembler;

    public PurchaseOrderAssembler() {
        super(SalesRestController.class, PurchaseOrderDTO.class);
    }

    public PurchaseOrderDTO toResource(PurchaseOrder purchaseOrder) {
        PurchaseOrderDTO dto = createResourceWithId(purchaseOrder.getId(), purchaseOrder);
        dto.set_id(purchaseOrder.getId());
        dto.setPlant(plantInventoryEntryAssembler.toResource(purchaseOrder.getPlant()));
        dto.setRentalPeriod(BusinessPeriodDTO.of(purchaseOrder.getRentalPeriod().getStartDate(),purchaseOrder.getRentalPeriod().getEndDate()));
        dto.setTotal(purchaseOrder.getTotal());
        dto.setStatus(purchaseOrder.getStatus());
        return dto;
    }
}