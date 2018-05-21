package com.buildit.procurement.application.services;

import com.buildit.procurement.application.dto.PurchaseOrderDTO;
import com.buildit.procurement.domain.enums.POStatus;
import com.buildit.procurement.domain.enums.RentItPurchaseOrderStatus;
import com.buildit.procurement.domain.model.PlantHireRequest;
import com.buildit.procurement.domain.model.PurchaseOrder;
import com.buildit.procurement.domain.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Service
public class PurchaseOrderService {

	@Autowired
	PurchaseOrderRepository repository;

	@Autowired
	PurchaseOrderAssembler assembler;

	@Autowired
	PlantHireRequestService plantHireRequestService;

	@Transactional
	public PurchaseOrder create(String href, POStatus status, Long plantHireRequestId) {
		requireNonNull(href);
		requireNonNull(status);
		if (href.length() < 10) throw new IllegalArgumentException("Illegal href: " + href);

		PurchaseOrder purchaseOrder = new PurchaseOrder();

		PlantHireRequest plantHireRequest = plantHireRequestService.readModel(plantHireRequestId);

		purchaseOrder.setHref(href);
		purchaseOrder.setStatus(status);
		purchaseOrder.setPlantHireRequest(plantHireRequest);

		purchaseOrder = repository.save(purchaseOrder);

		return purchaseOrder;
	}

	@Transactional(readOnly = true)
	public List<PurchaseOrderDTO> readAll() {
		return assembler.toResources(repository.findAll());
	}

	public PurchaseOrder readModel(String id) {
		Optional<PurchaseOrder> maybePurchaseOrder = repository.findById(id);

		if (!maybePurchaseOrder.isPresent()) {
			throw new IllegalArgumentException("Cannot find purchase order by id: " + id);
		}

		return maybePurchaseOrder.get();
	}

	@Transactional
	public void updateStatus(String id, RentItPurchaseOrderStatus status) {
		requireNonNull(status);

		PurchaseOrder purchaseOrder = readModel(id);

		purchaseOrder.setStatus(status.convertToLocal());

		repository.save(purchaseOrder);
	}

}
