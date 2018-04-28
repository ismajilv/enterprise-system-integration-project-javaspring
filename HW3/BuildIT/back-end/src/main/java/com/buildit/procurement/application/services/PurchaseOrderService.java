package com.buildit.procurement.application.services;

import com.buildit.procurement.application.dto.PurchaseOrderDTO;
import com.buildit.procurement.domain.enums.POStatus;
import com.buildit.procurement.domain.model.PurchaseOrder;
import com.buildit.procurement.domain.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PurchaseOrderService {

	@Autowired
	PurchaseOrderRepository repository;

	@Autowired
	PurchaseOrderAssembler assembler;

	@Transactional
	public PurchaseOrder create(String href, POStatus status) {
		PurchaseOrder purchaseOrder = new PurchaseOrder();

		purchaseOrder.setHref(href);
		purchaseOrder.setStatus(status);

		purchaseOrder = repository.save(purchaseOrder);

		return purchaseOrder;
	}

	@Transactional(readOnly = true)
	public List<PurchaseOrderDTO> readAll() {
		return assembler.toResources(repository.findAll());
	}

}
