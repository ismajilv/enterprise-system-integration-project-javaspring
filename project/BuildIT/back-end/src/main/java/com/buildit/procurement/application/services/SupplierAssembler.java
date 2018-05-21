package com.buildit.procurement.application.services;

import com.buildit.procurement.application.dto.SupplierDTO;
import com.buildit.procurement.domain.model.Supplier;
import com.buildit.procurement.web.controller.PlantHireRequestController;
import com.buildit.procurement.web.controller.SupplierController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class SupplierAssembler extends ResourceAssemblerSupport<Supplier, SupplierDTO> {

	public SupplierAssembler() {
		super(PlantHireRequestController.class, SupplierDTO.class);
	}

	@Override
	public SupplierDTO toResource(Supplier supplier) {
		SupplierDTO dto = createResourceWithId(supplier.getId(), supplier);

		dto.set_id(supplier.getId());
		dto.setName(supplier.getName());

		return dto;
	}

}
