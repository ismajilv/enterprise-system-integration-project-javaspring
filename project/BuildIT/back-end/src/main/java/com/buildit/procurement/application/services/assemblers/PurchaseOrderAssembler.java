package com.buildit.procurement.application.services.assemblers;

import com.buildit.procurement.application.dto.PurchaseOrderDTO;
import com.buildit.procurement.domain.model.PurchaseOrder;
import com.buildit.procurement.web.controller.PlantHireRequestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderAssembler extends ResourceAssemblerSupport<PurchaseOrder, PurchaseOrderDTO> {

	public PurchaseOrderAssembler() {
		super(PlantHireRequestController.class, PurchaseOrderDTO.class);

	}

	@Override
	public PurchaseOrderDTO toResource(PurchaseOrder order) {
		PurchaseOrderDTO dto = createResourceWithId(order.getHref(), order);

		dto.setHref(order.getHref());
		dto.setExternalId(order.getExternalId());

		return dto;
	}

}
